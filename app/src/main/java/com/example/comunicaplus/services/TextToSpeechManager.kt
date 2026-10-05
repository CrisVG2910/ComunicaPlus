package com.example.comunicaplus.services

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TextToSpeechManager(
    context: Context
) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? =
        TextToSpeech(
            context.applicationContext,
            this
        )

    private var inicializado = false

    private var errorInicializacion: String? = null

    override fun onInit(
        status: Int
    ) {

        if (status == TextToSpeech.SUCCESS) {

            val resultadoIdioma =
                textToSpeech?.setLanguage(
                    Locale.forLanguageTag("es-CL")
                )
                    ?: TextToSpeech.ERROR

            inicializado =
                resultadoIdioma != TextToSpeech.LANG_MISSING_DATA &&
                        resultadoIdioma != TextToSpeech.LANG_NOT_SUPPORTED

            if (inicializado) {

                /*
                 * Una velocidad levemente inferior
                 * favorece la comprensión.
                 */
                textToSpeech?.setSpeechRate(
                    0.95f
                )

                errorInicializacion = null

            } else {

                errorInicializacion =
                    "El dispositivo no dispone de una voz compatible con español."
            }

        } else {

            inicializado = false

            errorInicializacion =
                "No fue posible inicializar el servicio de texto a voz."
        }
    }

    fun hablar(
        texto: String
    ): Result<Unit> {

        if (texto.isBlank()) {

            return Result.failure(
                IllegalArgumentException(
                    "Debes escribir un mensaje antes de reproducirlo."
                )
            )
        }

        if (!inicializado) {

            return Result.failure(
                IllegalStateException(
                    errorInicializacion
                        ?: "El servicio de texto a voz aún no está disponible."
                )
            )
        }

        val resultado =
            textToSpeech?.speak(
                texto,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "comunicaplus_escribir"
            )
                ?: TextToSpeech.ERROR

        return if (
            resultado == TextToSpeech.SUCCESS
        ) {

            Result.success(Unit)

        } else {

            Result.failure(
                IllegalStateException(
                    "No fue posible reproducir el mensaje."
                )
            )
        }
    }

    fun detener() {

        textToSpeech?.stop()
    }

    fun liberar() {

        textToSpeech?.stop()
        textToSpeech?.shutdown()

        textToSpeech = null
        inicializado = false
    }
}