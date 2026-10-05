package com.example.comunicaplus.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class DeviceLocationService(
    context: Context
) {

    private val appContext =
        context.applicationContext

    private val locationClient =
        LocationServices
            .getFusedLocationProviderClient(
                appContext
            )

    fun tienePermisoUbicacion(): Boolean {

        val permisoPreciso =
            ContextCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val permisoAproximado =
            ContextCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        return permisoPreciso ||
                permisoAproximado
    }

    fun tienePermisoPreciso(): Boolean {

        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    suspend fun obtenerUbicacionActual():
            Result<Location> {

        if (!tienePermisoUbicacion()) {

            return Result.failure(
                SecurityException(
                    "No se concedió permiso para acceder a la ubicación."
                )
            )
        }

        return try {

            val prioridad =
                if (tienePermisoPreciso()) {

                    Priority
                        .PRIORITY_HIGH_ACCURACY

                } else {

                    Priority
                        .PRIORITY_BALANCED_POWER_ACCURACY
                }

            val cancellationToken =
                CancellationTokenSource()

            val ubicacion =
                locationClient
                    .getCurrentLocation(
                        prioridad,
                        cancellationToken.token
                    )
                    .await()

            if (ubicacion != null) {

                Result.success(
                    ubicacion
                )

            } else {

                Result.failure(
                    IllegalStateException(
                        "No fue posible obtener la ubicación actual."
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}