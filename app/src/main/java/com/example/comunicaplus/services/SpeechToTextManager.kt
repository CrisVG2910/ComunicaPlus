package com.example.comunicaplus.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class SpeechToTextManager(
    context: Context,
    private val onResult: (String) -> Unit,
    private val onStateChange: (Boolean) -> Unit,
    private val onError: (String) -> Unit
) {

    private val appContext =
        context.applicationContext

    private var speechRecognizer: SpeechRecognizer? =
        null

    init {

        if (
            SpeechRecognizer
                .isRecognitionAvailable(appContext)
        ) {

            speechRecognizer =
                SpeechRecognizer
                    .createSpeechRecognizer(
                        appContext
                    )

            speechRecognizer
                ?.setRecognitionListener(
                    createRecognitionListener()
                )

        } else {

            onError(
                "El reconocimiento de voz no está disponible en este dispositivo."
            )
        }
    }

    fun iniciarEscucha() {

        val recognizer =
            speechRecognizer

        if (recognizer == null) {

            onError(
                "El servicio de reconocimiento de voz no está disponible."
            )

            return
        }

        val intent =
            Intent(
                RecognizerIntent
                    .ACTION_RECOGNIZE_SPEECH
            ).apply {

                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent
                        .LANGUAGE_MODEL_FREE_FORM
                )

                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    "es-CL"
                )

                putExtra(
                    RecognizerIntent.EXTRA_MAX_RESULTS,
                    1
                )
            }

        try {

            recognizer.startListening(
                intent
            )

        } catch (e: Exception) {

            onStateChange(false)

            onError(
                "No fue posible iniciar el reconocimiento de voz."
            )
        }
    }

    fun detenerEscucha() {

        speechRecognizer
            ?.stopListening()
    }

    fun liberar() {

        speechRecognizer
            ?.cancel()

        speechRecognizer
            ?.destroy()

        speechRecognizer = null
    }

    private fun createRecognitionListener():
            RecognitionListener {

        return object :
            RecognitionListener {

            override fun onReadyForSpeech(
                params: Bundle?
            ) {

                onStateChange(true)
            }

            override fun onBeginningOfSpeech() {

                onStateChange(true)
            }

            override fun onRmsChanged(
                rmsdB: Float
            ) {
            }

            override fun onBufferReceived(
                buffer: ByteArray?
            ) {
            }

            override fun onEndOfSpeech() {

                onStateChange(false)
            }

            override fun onError(
                error: Int
            ) {

                onStateChange(false)

                onError(
                    mensajeError(error)
                )
            }

            override fun onResults(
                results: Bundle?
            ) {

                onStateChange(false)

                val textos =
                    results
                        ?.getStringArrayList(
                            SpeechRecognizer
                                .RESULTS_RECOGNITION
                        )

                val texto =
                    textos
                        ?.firstOrNull()

                if (
                    texto.isNullOrBlank()
                ) {

                    onError(
                        "No se pudo reconocer ninguna frase."
                    )

                } else {

                    onResult(texto)
                }
            }

            override fun onPartialResults(
                partialResults: Bundle?
            ) {
            }

            override fun onEvent(
                eventType: Int,
                params: Bundle?
            ) {
            }
        }
    }

    private fun mensajeError(
        error: Int
    ): String {

        return when (error) {

            SpeechRecognizer.ERROR_AUDIO ->
                "Se produjo un error al acceder al audio."

            SpeechRecognizer.ERROR_CLIENT ->
                "Se produjo un error al iniciar el reconocimiento."

            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                "No se concedió permiso para utilizar el micrófono."

            SpeechRecognizer.ERROR_NETWORK,
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT ->
                "No fue posible conectar con el servicio de reconocimiento."

            SpeechRecognizer.ERROR_NO_MATCH ->
                "No se pudo reconocer lo que se dijo."

            SpeechRecognizer.ERROR_RECOGNIZER_BUSY ->
                "El reconocimiento de voz está ocupado. Intenta nuevamente."

            SpeechRecognizer.ERROR_SERVER,
            SpeechRecognizer.ERROR_SERVER_DISCONNECTED ->
                "El servicio de reconocimiento no está disponible."

            SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                "No se detectó ninguna voz."

            else ->
                "Ocurrió un error durante el reconocimiento de voz."
        }
    }
}