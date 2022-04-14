package com.nova.exwrite.exercise;

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

public class ExAdapter extends RecyclerView.Adapter<ExAdapter.CustomViewHolder> {

    private ArrayList<ExData> arrayEx;
    Context context;
    String SharedPreFile = "exData";
    Gson gson;
    Type typeExdata = new TypeToken<ArrayList<ExData>>() {
    }.getType();
    int pos;

    public ExAdapter(Context context, ExAdapter.OnListItemSelectedInterface listener, ArrayList<ExData> arrayEx) {

        this.arrayEx = arrayEx;
        this.context = context;
        this.mListener = listener;
    }


    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView exImg;
        protected TextView exTitle;
        protected TextView exStart;
        protected TextView exTime;
        protected TextView exContents;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.exImg = itemView.findViewById(R.id.btn_exwrite_img);
            this.exTitle = itemView.findViewById(R.id.exTitle);
            this.exStart = itemView.findViewById(R.id.exStart);
            this.exTime = itemView.findViewById(R.id.exTime);
            this.exContents = itemView.findViewById(R.id.exContents);
            itemView.setOnClickListener(new View.OnClickListener() {
                //아이템 클릭 후 수정 페이지로 이동.
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());
                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(context, ExEdit.class);
                        intent.putExtra("position1", pos);
                        (context).startActivity(intent);
                    }

                }
            });
            //삭제 다이얼로그 구현
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String st[] = {"1.예", "2.아니오"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제하시겠습니까?");
                    builder.setItems(st, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i == 0) {
//                                remove(getLayoutPosition());
                                pos = getAdapterPosition();
                                gson = new Gson();

                                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreFile, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String bookDataD = sharedPreferences.getString("ExData", "");

                                arrayEx = gson.fromJson(bookDataD, typeExdata);
                                arrayEx.remove(pos);
                                String sharedExdata = gson.toJson(arrayEx, typeExdata);
                                editor.putString("ExData", sharedExdata);
                                editor.commit();


                                notifyDataSetChanged();


                            } else if (i == 1) {

                                Toast.makeText(context, "아니오", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });
        }

        public void onBind(ExData item) {
            Log.e("바인드데이터", "데이터");


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
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("뷰홀더", "생성");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exlist_item, viewGroup, false);
        CustomViewHolder viewholder = new CustomViewHolder(view);

        return viewholder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        Log.d("바인드뷰홀더", "바뷰홀더");
        viewholder.onBind(arrayEx.get(position));

    }

    @Override
    public int getItemCount() {
        Log.d("아이템수", String.valueOf(arrayEx.size()));
        return (null != arrayEx ? arrayEx.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void remove(int position) {
        try {
            arrayEx.remove(position);
            notifyItemRemoved(position);

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();

        }
    }
}
