package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

import es.source.code.R;

public class FoodDetailed extends Activity {
    private Logger log = Logger.getLogger("FoodDetailed");
    float x1 = 0.0f, x2 = 0.0f;
    private int currentFoodIndex = 0;//当前界面展示的菜品的索引
    private int foodTotalNums = 0;
    private TextView textViewFoodName;
    private TextView textViewFoodPrice;
    private ImageView imageViewFoodPhoto;
    private Button buttonUnsubscribe;
    private String foodNames[];
    private float foodPrices[];
    private int foodPhotos[];
    private int foodStates[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);

        textViewFoodName = findViewById(R.id.text_view_food_name_detailed);
        textViewFoodPrice = findViewById(R.id.text_view_food_price_detailed);
        imageViewFoodPhoto = findViewById(R.id.image_view_food_detail);
        buttonUnsubscribe = findViewById(R.id.button_unsubscribe_food_detail);

        //获取FoodView传来的参数
        Intent intent = getIntent();
        currentFoodIndex = intent.getIntExtra("foodIndex", 0);

        foodNames = intent.getStringArrayExtra("foodNames");
        foodPrices = intent.getFloatArrayExtra("foodPrices");
        foodPhotos = intent.getIntArrayExtra("foodPhotos");
        foodStates = intent.getIntArrayExtra("foodStates");
        foodTotalNums = foodNames.length;


        //根据参数改变组件上的文字
        textViewFoodName.setText(foodNames[currentFoodIndex]);
        textViewFoodPrice.setText(new Float(foodPrices[currentFoodIndex]).toString());
        imageViewFoodPhoto.setImageResource(foodPhotos[currentFoodIndex]);
        if (0 ==  foodStates[currentFoodIndex]) {
            buttonUnsubscribe.setText("点菜");
            buttonUnsubscribe.setBackgroundColor(Color.GREEN);
        } else {
            buttonUnsubscribe.setText("退点");
            buttonUnsubscribe.setBackgroundColor(Color.YELLOW);
        }

        //向左侧滑动监听器
        LinearLayout linearLayout = findViewById(R.id.layout_food_detailed);
        linearLayout.setClickable(true);
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    x1 = event.getX();
                }
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    x2 = event.getX();
                    //发生了左滑或者右滑动
                    if (10 <= Math.abs(x1 - x2)) {
                        //左滑
                        if (x1 > x2) {
                            //越界判断
                            log.info(">>>>>>>>>>currentFoodIndex:" + currentFoodIndex);
                            if (0 == currentFoodIndex) {
                                Toast.makeText(FoodDetailed.this, "已经是第一个菜品", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                            currentFoodIndex--;
                        } else {
                            //越界判断
                            if ((foodTotalNums-1) == currentFoodIndex) {
                                Toast.makeText(FoodDetailed.this, "已经是最后一个菜品", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                            currentFoodIndex++;
                        }
                        textViewFoodName.setText(foodNames[currentFoodIndex]);
                        textViewFoodPrice.setText(new Float(foodPrices[currentFoodIndex]).toString());
                        imageViewFoodPhoto.setImageResource(foodPhotos[currentFoodIndex]);
                        if (0 ==  foodStates[currentFoodIndex]) {
                            buttonUnsubscribe.setText("点菜");
                            buttonUnsubscribe.setBackgroundColor(Color.GREEN);
                        } else {
                            buttonUnsubscribe.setText("退点");
                            buttonUnsubscribe.setBackgroundColor(Color.YELLOW);
                        }
                    }

                }
                return true;
            }
        };
        linearLayout.setOnTouchListener(onTouchListener);

    }



}
