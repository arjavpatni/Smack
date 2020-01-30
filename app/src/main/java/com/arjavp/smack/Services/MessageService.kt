package com.arjavp.smack.Services
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.arjavp.smack.Controller.App
import com.arjavp.smack.Model.Channel
import com.arjavp.smack.Model.Message
import com.arjavp.smack.Utlities.URL_GET_CHANNELS
import com.arjavp.smack.Utlities.URL_GET_MESSAGES
import org.json.JSONException

// to download and store the channels & messages
object MessageService {

    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    fun getChannels(complete: (Boolean) -> Unit){

        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener {response ->

            try {
                //looping through the arrayList of JSON objects.
                for(x in 0 until response.length()){
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val chanDesc = channel.getString("description")
                    val channelId = channel.getString("_id")

                    val newChannel = Channel(name, chanDesc, channelId)
                    //add this to array list of channels
                    //"this" to access channels array list in this file.
                    this.channels.add(newChannel)
                }
                complete(true)
            }catch (e: JSONException){
                Log.d("JSON","EXC: "+ e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener {
            Log.d("ERROR","Could not retrieve channels")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }
        App.prefs.requestQueue.add(channelsRequest)
    }

    fun getMessages(channelId: String, complete: (Boolean)-> Unit){

        val url = "$URL_GET_MESSAGES$channelId"
        val messagesRequest = object : JsonArrayRequest(Method.GET, URL_GET_MESSAGES, null, Response.Listener { response ->
            clearMessages()//to make way for new messages (on switching to a new channel)
            try{
                for (x in 0 until response.length()){
                    val message = response.getJSONObject(x)
                    val messageBody =message.getString("messageBody") //get keys from index.js
                    val channelId = message.getString("channelId")
                    val id = message.getString("_id")
                    val userName = message.getString("userName")
                    val userAvatar = message.getString("userAvatar")
                    val userAvatarColor = message.getString("userAvatarColor")
                    val timeStamp = message.getString("timeStamp")

                    val newMessage = Message(messageBody, userName, channelId, userAvatar, userAvatarColor, id, timeStamp)
                    messages.add(newMessage)
                }
                complete(true)
            }catch (e: JSONException){
                Log.d("JSON","EXC: "+ e.localizedMessage)
                complete(false)
            }
        },
            Response.ErrorListener {
                Log.d("ERROR","Could not retrieve channels")
                complete(false)
            }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }
        }
        App.prefs.requestQueue.add(messagesRequest)
    }
    fun clearMessages(){
        messages.clear()
    }
    fun clearChannels(){
        channels.clear()
    }
}