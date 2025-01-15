package com.example.supportly.network

import android.util.Log
import com.example.supportly.model.Message
import com.example.supportly.network.RetrofitInstance.api
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.WebSocketListener
import org.json.JSONObject


class MyWebSocketListener(
    private val onValidationSuccess: (Any?, Any?, Any?) -> Unit // Callback con mentorId, estado y mensaje
) : WebSocketListener() {

    lateinit var mSocket: Socket

    init {
        // Inicializar el socket
        try {
            mSocket = IO.socket("//http://tr2g5.dam.inspedralbes.cat:23412")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("SocketIO", "Failed to connect to socket", e)
        }

        mSocket.connect()

        mSocket.on(Socket.EVENT_CONNECT) {
            Log.d("SocketIO", "Connected to socket: ${mSocket.id()}")
        }

        mSocket.on("mentor-validat") { args ->
            if (args.isNotEmpty()) {
                val data = args[0] as JSONObject
                try {
                    val mentorId = data.getString("mentorId")
                    val validado = data.getBoolean("validado")
                    val message = data.getString("message")

                    onValidationSuccess(mentorId, validado, message)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("SocketIO", "Error parsing mentor-validat event", e)
                }
            }
        }
        mSocket.on("mRecibido") { args ->
            if (args.isNotEmpty()) {
                val newMessage = args[0] as JSONObject
                try {
                    val sender = newMessage.getString("sender")
                    val receiver = newMessage.getString("receiver")
                    val message = newMessage.getString("message")
                    val timestamp = newMessage.getString("timestamp")

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("SocketIO", "Error parsing messageReceived event", e)
                }
            }
        }
    }

    fun disconnect() {
        mSocket.disconnect()
        mSocket.off("mentor-validat")
    }
}

