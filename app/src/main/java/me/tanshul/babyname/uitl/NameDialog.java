package me.tanshul.babyname.uitl;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import me.tanshul.babyname.R;
import me.tanshul.babyname.model.NameItem;

/**
 * Created by tanshul on 15/01/18.
 */

public class NameDialog extends DialogFragment {
    private Context mContext;
    private NameItem mNameItem;

    //Declare all views here
    CircleImageView ivIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setGravity(Gravity.CENTER);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_name);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ivIcon = (CircleImageView) dialog.findViewById(R.id.iv_icon);
        if (mNameItem.getType() == NameItem.GENDER.BOY) {
            ivIcon.setImageResource(R.drawable.ic_boy);
        } else {
            ivIcon.setImageResource(R.drawable.ic_girl);
        }
        ((TextView) dialog.findViewById(R.id.tv_name)).setText(mNameItem.getName());
        ((TextView) dialog.findViewById(R.id.tv_meaning)).setText(mNameItem.getMeaning());
        dialog.show();
        return dialog;
    }

    public void setDialogContent(Context context, NameItem nameItem) {
        this.mContext = context;
        this.mNameItem = nameItem;
    }
}
