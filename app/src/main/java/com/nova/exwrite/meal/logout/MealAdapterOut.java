package com.nova.exwrite.meal.logout;

import static android.content.ContentValues.TAG;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.exwrite.R;
import com.nova.exwrite.exercise.logout.ExAdapterOut;
import com.nova.exwrite.exercise.logout.ExDataOut;
import com.nova.exwrite.exercise.logout.ExEditOut;
import com.nova.exwrite.meal.MealListD;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MealAdapterOut extends RecyclerView.Adapter<MealAdapterOut.CustomViewHolder> {

    private ArrayList<MealDataOut> arrayMOut;
    Context context;
    String SharedPreFile = "MDataOut";
    Gson gson;
    Type typeMealdata = new TypeToken<ArrayList<MealDataOut>>() {
    }.getType();
    int pos;

    public MealAdapterOut(Context context, MealAdapterOut.OnListItemSelectedInterface listener, ArrayList<MealDataOut> arraymOut) {

        this.arrayMOut = arraymOut;
        this.context = context;
        this.mListener = listener;
    }


    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private MealAdapterOut.OnListItemSelectedInterface mListener;


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView mImg;
        protected TextView MTitle, MStart, MTime, MContents;
        protected TextView mTitle, mStart, mTime, mContents;
        int mealNumber;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.MTitle = itemView.findViewById(R.id.mealName);
            this.MStart = itemView.findViewById(R.id.mealTime);
            this.MTime = itemView.findViewById(R.id.mealAmount);
            this.MContents = itemView.findViewById(R.id.mealMemo);


            this.mImg = itemView.findViewById(R.id.meal_img);
            this.mTitle = itemView.findViewById(R.id.meal_name);
            this.mStart = itemView.findViewById(R.id.meal_time);
            this.mTime = itemView.findViewById(R.id.meal_amount);
            this.mContents = itemView.findViewById(R.id.meal_memo);
            itemView.setOnClickListener(new View.OnClickListener() {
                //아이템 클릭 후 수정 페이지로 이동.
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, MealEditOut.class);
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
                                Log.e("position", Integer.toString(pos));
                                SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreFile, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String mealDataD = sharedPreferences.getString("MDataOut", "");
                                Log.e("position", "아이템 크기" + arrayMOut.size());
                                arrayMOut = gson.fromJson(mealDataD, typeMealdata);
                                arrayMOut.remove(pos);
                                String sharedExdata = gson.toJson(arrayMOut, typeMealdata);
                                editor.putString("MDataOut", sharedExdata);
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

        public void onBind(MealDataOut item) {
            Log.e("바인드데이터", "데이터");


            byte[] arr = item.getMeal_pic();
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            mImg.setImageBitmap(image);
            mTitle.setText(item.getMtitle());
            mStart.setText(item.getMtime());
            mTime.setText(item.getMamount());
            mContents.setText(item.getMcontents());

        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("뷰홀더", "생성");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meal_item, viewGroup, false);
        CustomViewHolder viewholder = new CustomViewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        viewholder.onBind(arrayMOut.get(position));
    }


    @Override
    public int getItemCount() {
//        Log.d("아이템수", String.valueOf(arraym.size()));
        return (null != arrayMOut ? arrayMOut.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void remove(int position) {
        try {
            arrayMOut.remove(position);
            notifyItemRemoved(position);

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();

        }
    }
}
