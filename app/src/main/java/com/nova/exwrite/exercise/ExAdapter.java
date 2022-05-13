package com.nova.exwrite.exercise;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;
import com.nova.exwrite.bodywrite.server.BodyEdit2;
import com.nova.exwrite.bodywrite.server.BodyListD2;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class  ExAdapter extends RecyclerView.Adapter<ExAdapter.CustomViewHolder> {

    private ArrayList<ExData> arrayEx;
    Context context;

    int pos;
    RequestQueue queue;
    public ExAdapter(Context context, ArrayList<ExData> arrayEx) {

        this.arrayEx = arrayEx;
        this.context = context;
//        this.mListener = listener;
    }


    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView exImg;
        protected TextView exTitle;
        protected TextView exId;
        protected TextView exStart;
        protected TextView exTime;
        protected TextView exContents;
        protected SimpleDraweeView draweeView;
        int exNumber;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
//            Fresco.initialize(context);
            //액티비티가 아닌 곳에서 Context를 쓰기 위해 액티비티에서 Context 값을 가져옵니다.
            //액티비티

//            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);

//            this.exImg = itemView.findViewById(R.id.btn_exwrite_img);
            this.exId = itemView.findViewById(R.id.exID);
            this.exTitle = itemView.findViewById(R.id.exTitle);
            this.exStart = itemView.findViewById(R.id.exStart);
            this.exTime = itemView.findViewById(R.id.exTime);
            this.exContents = itemView.findViewById(R.id.exContents);
            itemView.setOnClickListener(new View.OnClickListener() {
                //아이템 클릭 후 수정 페이지로 이동.
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    exNumber = arrayEx.get(pos).getExNumber();
                    String extitle = arrayEx.get(pos).getExtitle();
                    String exid = arrayEx.get(pos).getExId();
                    String exstart = arrayEx.get(pos).getExstart();
                    String extime = arrayEx.get(pos).getExtime();
                    String excontents = arrayEx.get(pos).getExcontents();

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent edit_ex = new Intent(context, ExEdit.class);
                        edit_ex.putExtra("pos", pos);
                        edit_ex.putExtra("eExN", exNumber);
                        edit_ex.putExtra("eExT", extitle);
                        edit_ex.putExtra("eExI", exid);
                        edit_ex.putExtra("eExs", exstart);
                        edit_ex.putExtra("eExt", extime);
                        edit_ex.putExtra("eExc", excontents);
                        (context).startActivity(edit_ex);
                    }

                }
            });
            //삭제 다이얼로그 구현
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    queue = Volley.newRequestQueue(context);


                    String st[] = {"1.예", "2.아니오"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제하시겠습니까?");
                    builder.setItems(st, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (i == 0) {
//                                remove(getLayoutPosition());
                                pos = getAdapterPosition();
                                exNumber = arrayEx.get(pos).getExNumber();
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG, "db 삭제 후");

                                        arrayEx.remove(pos);
                                        notifyDataSetChanged();
                                    }
                                };
                                ExListD exListD = new ExListD(Integer.toString(exNumber), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(exListD);
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


//            byte[] arr = item.getEx_pic();
//            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//
//
//            exImg.setImageBitmap(image);
            exTitle.setText(item.getExtitle());
            exId.setText(item.getExId());
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

//        Uri uri = Uri.parse("C:\\Users\\solut\\exwrite\\html\\exList\\home.PNG");
//        viewholder.draweeView.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
//        Log.d("아이템수", String.valueOf(arrayEx.size()));
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
