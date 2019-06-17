package com.example.registration;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<EmailDetails> EmailDetails;
    private Context context;
    private String mailbox;
    private int id;
    public RecyclerAdapter(List EmailData, Context context){
        this.EmailDetails = EmailData;
        this.context = context;
    }

   class ViewHolder extends RecyclerView.ViewHolder {
        //define the view to be used for each data item
        TextView Icon;
        TextView Sender;
        TextView EmailTitle;
        TextView EmailDetails;
        TextView EmailTime;
        RelativeLayout layout;

        public ViewHolder(View view){
            super(view);
            Icon = itemView.findViewById(R.id.tvIcon);
            Sender = itemView.findViewById(R.id.tvEmailSender);
            EmailTitle = itemView.findViewById(R.id.tvEmailTitle);
            EmailDetails = itemView.findViewById(R.id.tvEmailDetails);
            EmailTime = itemView.findViewById(R.id.tvEmailTime);
            layout = itemView.findViewById(R.id.layout);
        }
    }
    //create new views
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mail_item,
                parent, false);
        return new ViewHolder(view);
    }
    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        //get element from your dataset at this position
        //replace the contents of the view with that element
        holder.Icon.setText(EmailDetails.get(position).getSender().substring(0,1));
        holder.Sender.setText(EmailDetails.get(position).getSender());
        holder.EmailTitle.setText(EmailDetails.get(position).getTitle());
        holder.EmailDetails.setText(EmailDetails.get(position).getSummary());
        holder.EmailTime.setText(EmailDetails.get(position).getTime());
        mailbox = EmailDetails.get(position).getMailbox();
        id = EmailDetails.get(position).getId();
        Random rand = new Random();
        final int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        ((GradientDrawable) holder.Icon.getBackground()).setColor(color);

      holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowEmail.class);
                intent.putExtra("sender", holder.Sender.getText().toString());
                intent.putExtra("title", holder.EmailTitle.getText().toString());
                intent.putExtra("details", holder.EmailDetails.getText().toString());
                intent.putExtra("time", holder.EmailTime.getText().toString());
                intent.putExtra("icon", holder.Icon.getText().toString());
                intent.putExtra("colorIcon",color);
                intent.putExtra("box", mailbox);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount(){
        return EmailDetails.size();
    }
}
