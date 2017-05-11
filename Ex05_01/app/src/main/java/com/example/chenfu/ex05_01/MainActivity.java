package com.example.chenfu.ex05_01;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Spot> Spot_Activity = Spot_add_data();//建立List容器內的資料
    private ListView listView_show_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView_show_data = (ListView) findViewById(R.id.listView_show_data);//建立與xml的關聯
        listView_show_data.setAdapter(ProductsAdapter);
        ListView_click_event();
    }

    private void ListView_click_event() {


        AdapterView.OnItemClickListener ListView_OnItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Spot spot = Spot_Activity.get(position);
                String Name = spot.getName();
                String Prise = spot.getAddress();
                Toast.makeText(MainActivity.this, "名稱:" + Name + "價格:" + Prise, Toast.LENGTH_SHORT).show();

            }

        };

        listView_show_data.setOnItemClickListener(ListView_OnItemClick);
    }



    ProductsAdapter ProductsAdapter = new ProductsAdapter(Spot_Activity, MainActivity.this);



/*自行定義我們需要的Adapter來配合item_layout*/
class ProductsAdapter extends BaseAdapter {
    private List<Spot> List_Spots;
    private Context context;

    /*ProductsAdapter(List 型別,位置)*/
    public ProductsAdapter(List<Spot> spots, Context context) {
        this.List_Spots = spots;
        this.context = context;
    }


    @Override
        /*getView是從父類別Adapter繼承來的*/
    public View getView(final int position, View convertView, ViewGroup parent) {
        //getiew(index,框,框所需的參數)
        //Log.e("Show~parent(框所需的參數)~~~~",parent.toString());
        final Spot Get_Data_from_List = List_Spots.get(position);//使用position的值取出List容器內的對應資料

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //LayoutInflater (專門用來動態載入的載入器) .from(誰要被載入)
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_layout, parent, false);/*新增layout到XML(動態載入)*/
            //  inflate( 我們定義框的樣式xml ,框所需的參數,??????)
        }


        final ImageView item_layout_ImageView = (ImageView) convertView.findViewById(R.id.item_layout_image);
        item_layout_ImageView.setImageResource(Get_Data_from_List.getImageId());//放圖片
        item_layout_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img_for_Toast = new ImageView(MainActivity.this);
                img_for_Toast.setImageResource(Get_Data_from_List.getImageId());//放圖片
                Toast tos = new Toast(MainActivity.this);
                tos.setView(img_for_Toast);
                tos.setDuration(Toast.LENGTH_SHORT);
                tos.show();
            }
        });
        TextView item_layout_Name = (TextView) convertView.findViewById(R.id.item_layout_name);
        item_layout_Name.setText(Get_Data_from_List.getName());//放商品名稱
        item_layout_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spot spot = Spot_Activity.get(position);
                Toast.makeText(MainActivity.this, spot.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        TextView item_layout_Price = (TextView) convertView.findViewById(R.id.item_layout_price);
        item_layout_Price.setText(Get_Data_from_List.getAddress());//放商品價格
        item_layout_Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spot spot = Spot_Activity.get(position);
                Toast.makeText(MainActivity.this, "NT:" + spot.getAddress(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;

    }

    @Override
    public int getCount() {
        return List_Spots.size();//回傳 List內的總數
    }

    @Override
    public Object getItem(int position) {
        return List_Spots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return List_Spots.get(position).getImageId();
    }

}

    /*將資料塞入List容器中(Spot為定義List容器的規格)*/
    private List<Spot> Spot_add_data() {
        List<Spot> Spots = new ArrayList<>();
        Spots.add(new Spot(16, R.drawable.pic01_samsung_gakaxy_c9_pro, "Summung Galaxy C9 Pro", "16900"));
        Spots.add(new Spot(15, R.drawable.pic02_sony_xperia_xz, "Sony Xperia XZ", "15900"));
        Spots.add(new Spot(14, R.drawable.pic03_sony_xperia_xa, "Sony Xperia XA", "7700"));
        Spots.add(new Spot(13, R.drawable.pic04_xiaomi_note_4x, "Xiaomi Note 4X", "4700"));
        Spots.add(new Spot(12, R.drawable.pic05_samsung_gakaxy_a7, "Samsung Gakaxy A7", "13200"));
        Spots.add(new Spot(11, R.drawable.pic06_htc_10, "HTC 10", "14060"));
        Spots.add(new Spot(10, R.drawable.pic07_oppo_r9s, "OPPO R9s", "11800"));
        Spots.add(new Spot(9, R.drawable.pic08_moto_z, "Moto Z", "16500"));
        Spots.add(new Spot(8, R.drawable.pic09_asus_zenfone_3, "ASUS Zenfone 3", "14380"));
        Spots.add(new Spot(7, R.drawable.pic10_huawei_mate, "HUAWEI Mate", "16700"));
        Spots.add(new Spot(6, R.drawable.pic11_htc_u_play, "HTC_U_Play", "11450"));
        Spots.add(new Spot(5, R.drawable.pic12_htc_u_ultra, "HTC_U_Ultra", "19200"));
        Spots.add(new Spot(4, R.drawable.pic13_samsung_galaxy_a5, "Samsung_Galaxy_A5", "9500"));
        Spots.add(new Spot(3, R.drawable.pic14_xiaomi_note_2, "Xiaomi Note 2", "16200"));
        Spots.add(new Spot(2, R.drawable.pic15_xiaomi_5s_plus, "Xiaomi_5s_Plus", "11100"));
        Spots.add(new Spot(1, R.drawable.pic16_apple_iphone7, "Apple_Iphone7", "24800"));
        return Spots;
    }
}
