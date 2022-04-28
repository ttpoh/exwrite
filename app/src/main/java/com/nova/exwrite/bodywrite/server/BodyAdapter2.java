package com.nova.exwrite.bodywrite.server;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.nova.exwrite.R;

import java.util.ArrayList;

public class BodyAdapter2 extends RecyclerView.Adapter<BodyAdapter2.CustomViewHolder> {

    private ArrayList<BodyData2> arrayBody;
    final Context context;
    int pos;
    RequestQueue queue;

    public BodyAdapter2(Context context, ArrayList<BodyData2> arrayBody) {

        this.arrayBody = arrayBody;
        this.context = context;
//        this.mListener = listener;
    }


    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private BodyAdapter2.OnListItemSelectedInterface mListener;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView bodyImg;

        //        protected TextView bodyWriter;
        protected TextView bodyWeight;
        protected TextView bodyMuscle;
        protected TextView bodyFat;
        protected TextView bodyContents;
        int bodyNumber;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

//            this.bodyImg = itemView.findViewById(R.id.btn_bodywrite_img2);


//            this.bodyWriter = itemView.findViewById(R.id.bodyWriter2);
            this.bodyWeight = itemView.findViewById(R.id.bodyWeight2);
            this.bodyMuscle = itemView.findViewById(R.id.bodyMuscle2);
            this.bodyFat = itemView.findViewById(R.id.bodyFat2);
            this.bodyContents = itemView.findViewById(R.id.bodyContents2);
            itemView.setOnClickListener(new View.OnClickListener() {
                //아이템 클릭 후 수정 페이지로 이동.
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();

                    bodyNumber = arrayBody.get(pos).getBodyNumber();
                    String bodynickname = arrayBody.get(pos).getBodyNickname();
                    String bodyweight = arrayBody.get(pos).getBodyweight();
                    String bodymuscle = arrayBody.get(pos).getBodymuscle();
                    String bodyfat = arrayBody.get(pos).getBodyfat();
//                    String bodycontents = arrayBody.get(pos).getBodycontents();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent edit_body = new Intent(context, BodyEdit2.class);
                        edit_body.putExtra("position1", pos);
                        edit_body.putExtra("ebodyN", bodyNumber);
                        edit_body.putExtra("ebodyW", bodynickname);
                        edit_body.putExtra("ebodyw", bodyweight);
                        edit_body.putExtra("ebodym", bodymuscle);
                        edit_body.putExtra("ebodyf", bodyfat);
//                        edit_body.putExtra("ebodyc", bodycontents);


                        (context).startActivity(edit_body);
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
//                                String bodyweight = bodyWeight.getText().toString();
                                pos = getAdapterPosition();
                                bodyNumber = arrayBody.get(pos).getBodyNumber();
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d(TAG, "db 삭제 후");

                                        arrayBody.remove(pos);
                                        notifyDataSetChanged();
                                    }
                                };
                                BodyListD2 bodyList2 = new BodyListD2(Integer.toString(bodyNumber), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(bodyList2);

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

        public void onBind(BodyData2 item) {
            Log.e("바인드데이터", "데이터");


//            byte[] arr = item.getBody_pic();
//            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);


//            bodyImg.setImageBitmap(image);
            bodyWeight.setText(item.getBodyNickname());
            bodyMuscle.setText(item.getBodyweight());
            bodyFat.setText(item.getBodymuscle());
            bodyContents.setText(item.getBodyfat());

        }

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("뷰홀더", "생성");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.body_item2, viewGroup, false);
        CustomViewHolder viewholder = new CustomViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

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
