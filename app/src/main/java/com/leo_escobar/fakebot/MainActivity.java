package com.leo_escobar.fakebot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.leo_escobar.fakebot.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar RecyclerView y su adaptador
        chatAdapter = new ChatAdapter(getChatMessages());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecycler.setLayoutManager(layoutManager);
        binding.chatRecycler.setAdapter(chatAdapter);


        // Configurar el botón de envío
        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageInput.getText().toString().trim();

                if (!TextUtils.isEmpty(message)) {
                    // Agregar el mensaje del usuario
                    addMessage(new ChatMessage(message, null));
                    scrollToLastMessage();

                    // Retrasar la respuesta del bot en 1 o 2 segundos
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Obtener la respuesta del bot y agregarla
                            String botResponse = getRandomBotResponse();
                            addMessage(new ChatMessage(botResponse, getRandomBotResponse()));

                            // Hacer scroll hasta el último mensaje
                            scrollToLastMessage();
                        }
                    }, new Random().nextInt(1000) + 1000); // Esperar entre 1 y 2 segundos antes de responder

                    // Limpiar el campo de texto
                    binding.messageInput.getText().clear();
                }
            }
        });

    }

    // Agregar un mensaje al adaptador del RecyclerView
    private void addMessage(ChatMessage message) {
        chatAdapter.addMessage(message);
    }

    // Obtener la lista de mensajes del chat
    private List<ChatMessage> getChatMessages() {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage("", "Hola, ¿en qué puedo ayudarte?"));
        return chatMessages;
    }

    // Obtener una respuesta aleatoria del bot
    private String getRandomBotResponse() {
        String[] botResponses = {"Sí", "No", "Pregunta de nuevo",
                "Es muy probable", "No lo creo", "No sé \uD83D\uDE41","Tal vez"};
        Random random = new Random();
        return botResponses[random.nextInt(botResponses.length)];
    }

    // Hacer scroll hasta el último mensaje del RecyclerView
    private void scrollToLastMessage() {
        binding.chatRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int lastItemIndex = chatAdapter.getItemCount() - 1;
                binding.chatRecycler.smoothScrollToPosition(lastItemIndex);
            }
        }, 100);
    }
}
