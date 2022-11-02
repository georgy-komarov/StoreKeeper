package ml.komarov.itemscan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import ml.komarov.itemscan.databinding.ActivityBacodeBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class BarcodeActivity : AppCompatActivity() {
    private var _binding: ActivityBacodeBinding? = null
    private val binding: ActivityBacodeBinding get() = _binding!!

    private val barcodeOptions = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_EAN_13
        )
        .build()

    private val barcodeScanner = BarcodeScanning.getClient(barcodeOptions)

    private val barcodeExecutor: ExecutorService = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors()
    )

    @SuppressLint("UnsafeOptInUsageError")
    private val barcodeAnalyser: ImageAnalysis.Analyzer = ImageAnalysis.Analyzer { imageProxy ->
        val mediaImage = imageProxy.image ?: return@Analyzer
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.firstOrNull()?.let {
                    (this@BarcodeActivity::processBarcodeResult)(it)
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBacodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.v(TAG, "Number of cores ${Runtime.getRuntime().availableProcessors()}")
        askPermission(*REQUIRED_PERMISSION) {
            startCameraPreview()
        }.onDeclined {
            // Handle denied permissions here
        }
    }

    private fun startCameraPreview() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this@BarcodeActivity)

        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()

                // Camera Preview Setup
                val cameraPreview = Preview.Builder()
                    .build()
                    .also { previewBuilder ->
                        previewBuilder.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                    }

                // Preview frame analysis
                val imageAnalysis = ImageAnalysis.Builder()
                    .setImageQueueDepth(1)
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(barcodeExecutor, barcodeAnalyser)

                // Hook every thing in camera preview
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this@BarcodeActivity,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    imageAnalysis,
                    cameraPreview
                )
            },
            ContextCompat.getMainExecutor(this@BarcodeActivity)
        )
    }

    override fun onDestroy() {
        if (!barcodeExecutor.isShutdown) {
            barcodeExecutor.shutdown()
        }
        super.onDestroy()
    }

    private fun processBarcodeResult(barcodeResult: Barcode) {
        Log.d(TAG, "Barcode detected: ${barcodeResult.displayValue}")

        val intent = Intent()
        intent.putExtra("DATA", barcodeResult.rawValue)
        setResult(RESULT_OK, intent)
        finish()

    }

    companion object {
        const val TAG = "BarcodeActivity"
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    }
}