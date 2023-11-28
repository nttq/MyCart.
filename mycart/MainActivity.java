package edu.hanu.mycart;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button cartBtn;
    private EditText editSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvProduct = findViewById(R.id.rcvProduct);
        editSearch = findViewById(R.id.editSearch);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this);
        rcvProduct.setAdapter(productAdapter);
        rcvProduct.setLayoutManager(new LinearLayoutManager(this));
        cartBtn = findViewById(R.id.Cart);
        loadProduct();

        //change screen
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = editSearch.getText().toString();
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            List<Product> filteredList = new ArrayList<>();
                            for (Product product : productList) {
                                if (product.getName().contains(keyword)) {
                                    filteredList.add(product);
                                }
                            }
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

    private void loadProduct() {


        Handler handler = new Handler(Looper.getMainLooper());
        Constants.executorService.execute(new Runnable() {
            @Override
            public void run() {
                String json = loadJSON("https://hanu-congnv.github.io/mpr-cart-api/products.json");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(json==null){
                            Toast.makeText(MainActivity.this,"load failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            private String loadJSON(String link) {
                return link;
            }
        });
    }
}
