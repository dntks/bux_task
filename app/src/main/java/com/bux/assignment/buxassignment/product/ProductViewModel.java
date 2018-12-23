package com.bux.assignment.buxassignment.product;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bux.assignment.buxassignment.error.BuxErrorException;
import com.bux.assignment.buxassignment.error.ProductNotFoundException;
import com.bux.assignment.buxassignment.product.model.Product;
import com.bux.assignment.buxassignment.product.model.ServiceProduct;
import com.bux.assignment.buxassignment.product.updater.ProductUpdater;
import com.bux.assignment.buxassignment.product.updater.ProductUpdaterImpl;
import com.bux.assignment.buxassignment.product.updater.ProductUpdaterNullObject;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class ProductViewModel extends ViewModel {

    private final BuxService buxService = new BuxService(new OkHttpClient());

    private MutableLiveData<Product> productMutableLiveData;

    private ProductUpdater productUpdater = new ProductUpdaterNullObject();

    private ProductActivity.ProductErrorListener productErrorListener;

    public LiveData<Product> getProductMutableLiveData(String productId, final String previousId) {
        if (productMutableLiveData == null) {
            productMutableLiveData = new MutableLiveData<>();
            productUpdater = new ProductUpdaterImpl(productMutableLiveData);
            loadUsers(productId, previousId);
        }
        return productMutableLiveData;
    }

    private void loadUsers(final String productId, final String previousId) {
        Observable.fromCallable(new Callable<ServiceProduct>() {
            @Override
            public ServiceProduct call() throws Exception {
                return buxService.callServer(productId);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ServiceProduct>() {
            @Override
            public void accept(ServiceProduct serviceProduct) throws Exception {
                Product product = new Product(serviceProduct.getSecurityId(), serviceProduct.getDisplayName(), serviceProduct.getCurrentPrice(), serviceProduct.getClosingPrice());
                productMutableLiveData.setValue(product);
                buxService.callServerWebSocket(productUpdater, productId, previousId);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (productErrorListener != null) {
                    productErrorListener.onProductError();
                    if (throwable instanceof ProductNotFoundException) {
                        productErrorListener.onProductError("Product not found with id: " + productId);
                    } else if(throwable instanceof BuxErrorException){
                        productErrorListener.onProductError(((BuxErrorException)throwable).getBuxError());
                    }
                }
            }
        });
    }

    @Override
    protected void onCleared() {
        buxService.stopWebSocket();
    }

    public void setProductErrorListener(ProductActivity.ProductErrorListener productErrorListener) {
        this.productErrorListener = productErrorListener;
    }
}
