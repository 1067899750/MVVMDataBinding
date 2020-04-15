package com.example.z_lib_base.base.view;

public interface ICustomView<VM extends BaseCustomViewModel> {

    void setData(VM data);

    void setStyle(int resId);

    void setActionListener(ICustomViewActionListener listener);

}
