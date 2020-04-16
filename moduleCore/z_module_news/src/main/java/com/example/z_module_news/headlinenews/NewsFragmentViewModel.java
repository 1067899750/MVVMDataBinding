package com.example.z_module_news.headlinenews;

import android.app.Application;

import com.example.z_lib_base.base.BaseModel;
import com.example.z_lib_base.base.BaseViewModel;
import com.example.z_lib_base.bus.command.BindingAction;
import com.example.z_lib_base.bus.command.BindingCommand;
import com.example.z_lib_base.model.MvvmNetworkObserver;
import com.example.z_lib_net.base.BaseObserver;
import com.example.z_lib_net.NewNetworkApi;
import com.example.z_module_news.app.NewsApiInterface;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * @author puyantao
 * @describe
 * @create 2020/4/15 15:43
 */
public class NewsFragmentViewModel extends BaseViewModel<NewFragmentModel> {
    public ObservableField<String> name = new ObservableField<>();

    public NewsFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public NewFragmentModel initModel() {
        return new NewFragmentModel();
    }



    public BindingCommand requestNet = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            model.initNet();
        }
    });

}












