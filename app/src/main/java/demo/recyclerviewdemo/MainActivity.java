package demo.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button button;
    private Button button1;
    private HomeAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        button1 = (Button) findViewById(R.id.button1);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(2);
                addData(4);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(2);
                removeData(4);
            }
        });
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));//实现随机高度的瀑布流
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));//实现4例的gridview效果
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置item动画
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter(getApplication(),mDatas));
        mAdapter.setOnItemClickLitener(new HomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " click",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + " long click",
                        Toast.LENGTH_SHORT).show();
                removeData(position);
            }
        });
    }
    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    //为adapter中添加了两个方法：
    public void addData(int position) {
        mDatas.add(position, "Insert One");
        mAdapter.notifyItemInserted(position);
    }

    /*注意，这里更新数据集不是用adapter.notifyDataSetChanged()而是
    notifyItemInserted(position)与notifyItemRemoved(position)
    否则没有动画效果。*/
    public void removeData(int position) {
        mDatas.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
}
