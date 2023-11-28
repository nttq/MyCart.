package edu.hanu.mycart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> productList;
    List<Product> products;
    public ProductAdapter(List<Product> productList, MainActivity mainActivity) {
        this.productList = productList;
        this.products=productList;

    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
       View view=  inflater.inflate(R.layout.item_product, parent, false);
        return new ProductHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void filterList(ArrayList<Product> arrayList) {
        productList = arrayList;
        notifyDataSetChanged();
    }

    //chứa các thành phần giao diện người dùng cần thiết để hiển thị thông tin product trong productList, cung cấp cho phương thức onBindViewHolder() có thể được tái sử dụng và cập nhận với dữ liệu mới khi hiển thị

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView ivProduct;
        private TextView tvName, tvPrice;
        private ImageButton addtoCart;
        private Context context;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            this.context = context;

            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            addtoCart = itemView.findViewById(R.id.addtoCart);
        }

         public void bind(Product product) {
            tvName.setText(product.getName());
            tvPrice.setText(String.valueOf(product.getUnitPrice()));
            ImageLoader imgLoader = new ImageLoader();
            imgLoader.execute(product.getThumbnail());
            addtoCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductManager pManager = ProductManager.getInstance(context);
                    Product product1 = pManager.findByName(product.getName());

                    if(product1.getQuantity()==0){
                        product1.setQuantity(1);
                        pManager.update(product1);
                        Log.i("Product", String.valueOf(pManager.update(product)));
                        Toast.makeText(context,"Add successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context,"Added", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        public class ImageLoader extends AsyncTask<String, Void, Bitmap>{

            @Override
            protected Bitmap doInBackground(String... strings) {
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL(strings[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    return image;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                ivProduct.setImageBitmap(b);
            }
        }
    }

}
