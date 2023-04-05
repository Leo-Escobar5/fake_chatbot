package com.leo_escobar.fakebot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatMessage> chatMessages;

    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        if (chatMessage.getBotResponse() == null) {
            holder.userContainer.setVisibility(View.VISIBLE);
            holder.botContainer.setVisibility(View.GONE);
            holder.userMessage.setText(chatMessage.getUserMessage());
        } else {
            holder.userContainer.setVisibility(View.GONE);
            holder.botContainer.setVisibility(View.VISIBLE);
            holder.botMessage.setText(chatMessage.getBotResponse());
        }
    }

    public void addMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
        notifyItemInserted(chatMessages.size() - 1);
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout userContainer;
        private TextView userMessage;
        private RelativeLayout botContainer;
        private TextView botMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            userContainer = itemView.findViewById(R.id.user_container);
            userMessage = itemView.findViewById(R.id.user_message);
            botContainer = itemView.findViewById(R.id.bot_container);
            botMessage = itemView.findViewById(R.id.bot_message);
        }
    }
}
