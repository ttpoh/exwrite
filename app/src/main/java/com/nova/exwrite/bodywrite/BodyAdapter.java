package com.nova.exwrite.bodywrite;

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
import com.nova.exwrite.exercise.ExAdapter;
import com.nova.exwrite.exercise.ExData;
import com.nova.exwrite.exercise.ExEdit;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BodyAdapter extends RecyclerView.Adapter<BodyAdapter.CustomViewHolder> {

    private ArrayList<BodyData> arrayBody;
    Context context;
    String SharedPreFile = "bodyData";
    Gson gson;
    Type typeBodydata = new TypeToken<ArrayList<BodyData>>() {
    }.getType();
    int pos;

    public BodyAdapter(Context context, BodyAdapter.OnListItemSelectedInterface listener, ArrayList<BodyData> arrayBody) {

        this.arrayBody = arrayBody;
        this.context = context;
        this.mListener = listener;
    }


    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private BodyAdapter.OnListItemSelectedInterface mListener;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView bodyImg;
        protected TextView bodyWeight;
        protected TextView bodyMuscle;
        protected TextView bodyFat;
        protected TextView bodyContents;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.bodyImg = itemView.findViewById(R.id.btn_bodywrite_img);
            this.bodyWeight = itemView.findViewById(R.id.bodyMuscle);
            this.bodyMuscle = itemView.findViewById(R.id.bodyWeight);
            this.bodyFat = itemView.findViewById(R.id.bodyFat);
            this.bodyContents = itemView.findViewById(R.id.bodyContents);
            itemView.setOnClickListener(new View.OnClickListener() {
                //아이템 클릭 후 수정 페이지로 이동.
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());
                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(context, BodyEdit.class);
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
                                String bodyDataD = sharedPreferences.getString("BodyData", "");

                                arrayBody = gson.fromJson(bodyDataD, typeBodydata);
                                arrayBody.remove(pos);
                                String sharedBodydata = gson.toJson(arrayBody, typeBodydata);
                                editor.putString("BodyData", sharedBodydata);
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

        public void onBind(BodyData item) {
            Log.e("바인드데이터", "데이터");


            byte[] arr = item.getBody_pic();
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);


            bodyImg.setImageBitmap(image);
            bodyWeight.setText(item.getBodyweight());
            bodyMuscle.setText(item.getBodymuscle());
            bodyFat.setText(item.getBodyfat());
            bodyContents.setText(item.getBodycontents());

        }
    }

    @NonNull
    @Override
    public BodyAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("뷰홀더", "생성");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.body_item, viewGroup, false);
        BodyAdapter.CustomViewHolder viewholder = new BodyAdapter.CustomViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull BodyAdapter.CustomViewHolder viewholder, int position) {

        Log.d("바인드뷰홀더", "바뷰홀더");
        viewholder.onBind(arrayBody.get(position));
    }
    @Override
    public int getItemCount() {
        Log.d("아이템수", String.valueOf(arrayBody.size()));
        return (null != arrayBody ? arrayBody.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void remove(int position) {
        try {
            arrayBody.remove(position);
            notifyItemRemoved(position);

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();

        }
    }
}
