package com.nova.exwrite.exercise.logout;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExAdapterOut extends RecyclerView.Adapter<ExAdapterOut.CustomViewHolder> {

    private ArrayList<ExDataOut> arrayExOut;
    Context context;

    String SharedPreFile = "ExDataOut";
    Gson gson;
    Type typeExdata = new TypeToken<ArrayList<ExDataOut>>() {
    }.getType();
    int pos;


    public ExAdapterOut(Context context, ExAdapterOut.OnListItemSelectedInterface listener, ArrayList<ExDataOut> arrayExOut) {

        this.arrayExOut = arrayExOut;
        this.context = context;
        this.mListener = listener;
    }


    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private ExAdapterOut.OnListItemSelectedInterface mListener;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView exImg;
        protected TextView exTitle;
        protected TextView exStart;
        protected TextView exTime;
        protected TextView exContents;
//        int exNumber;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.exImg = itemView.findViewById(R.id.btn_exwrite_img);
            this.exTitle = itemView.findViewById(R.id.exTitle);
            this.exStart = itemView.findViewById(R.id.exStart);
            this.exTime = itemView.findViewById(R.id.exTime);
            this.exContents = itemView.findViewById(R.id.exContents);
            itemView.setOnClickListener(new View.OnClickListener() {
                //????????? ?????? ??? ?????? ???????????? ??????.
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, ExEditOut.class);
                        intent.putExtra("position1", pos);
                        (context).startActivity(intent);
                    }

                }
            });
            //?????? ??????????????? ??????
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    String st[] = {"1.???", "2.?????????"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("?????????????????????????");
                    builder.setItems(st, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i == 0) {
//                                remove(getLayoutPosition());
                                pos = getAdapterPosition();
                                gson = new Gson();
                                Log.e("position", Integer.toString(pos));
                                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreFile, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String exDataD = sharedPreferences.getString("ExDataOut", "");
                                Log.e("position", "????????? ??????"+arrayExOut.size());
                                arrayExOut = gson.fromJson(exDataD, typeExdata);
                                arrayExOut.remove(pos);
                                String sharedExdata = gson.toJson(arrayExOut, typeExdata);
                                editor.putString("ExDataOut", sharedExdata);
                                editor.commit();

                                notifyDataSetChanged();


                            } else if (i == 1) {

                                Toast.makeText(context, "?????????", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });
        }

        public void onBind(ExDataOut item) {
            Log.e("??????????????????", "?????????");


            byte[] arr = item.getEx_pic();
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);


            exImg.setImageBitmap(image);
            exTitle.setText(item.getExtitle());
            exStart.setText(item.getExstart());
            exTime.setText(item.getExtime());
            exContents.setText(item.getExcontents());

        }
    }

    @NonNull
    @Override
    public ExAdapterOut.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("?????????", "??????");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exlist_item, viewGroup, false);
        ExAdapterOut.CustomViewHolder viewholder = new ExAdapterOut.CustomViewHolder(view);

        return viewholder;
    }


    @Override
    public void onBindViewHolder(@NonNull ExAdapterOut.CustomViewHolder viewholder, int position) {

        Log.d("??????????????????", "????????????");
        viewholder.onBind(arrayExOut.get(position));

    }

    @Override
    public int getItemCount() {
//        Log.d("????????????", String.valueOf(arrayEx.size()));
        return (null != arrayExOut ? arrayExOut.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void remove(int position) {
        try {
            arrayExOut.remove(position);
            notifyItemRemoved(position);

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();

        }
    }
}
