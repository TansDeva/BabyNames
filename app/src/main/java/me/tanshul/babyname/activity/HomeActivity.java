package me.tanshul.babyname.activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.tanshul.babyname.R;
import me.tanshul.babyname.adapter.HomeAdapter;
import me.tanshul.babyname.adapter.IndexAdapter;
import me.tanshul.babyname.model.NameItem;
import me.tanshul.babyname.uitl.NameDialog;
import me.tanshul.babyname.uitl.PixelUtil;
import me.tanshul.babyname.uitl.Utility;
import me.tanshul.babyname.uitl.ValueCallback;

/**
 * Created by tansdeva on 15/01/18.
 */

public class HomeActivity extends BaseActivity {
    private int mType = 0;
    private NameDialog mDialog;
    private HomeAdapter mAdapter;
    private IndexAdapter mIndexAdapter;
    private boolean isSearchView = false;
    private ArrayList<NameItem> mList = new ArrayList<>();
    private ArrayList<NameItem> mItems = new ArrayList<>();
    private ArrayList<String> mIndexList = new ArrayList<>();
    private ArrayList<String> mListType = Utility.getList(R.array.list_type);
    private static final long ANIMATION_DURATION = 500;

    //Declare all views here
    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.tb_title)
    Toolbar mToolbar;
    @BindView(R.id.sp_list_type)
    Spinner spListType;
    @BindView(R.id.iv_menu_search)
    ImageView ivMenuSearch;
    @BindView(R.id.rl_search_view)
    RelativeLayout rlSearchView;
    @BindView(R.id.et_search_text)
    EditText etSearchText;
    @BindView(R.id.iv_menu_cancel)
    ImageView ivMenuCancel;
    @BindView(R.id.rv_item_list)
    RecyclerView rvItemList;
    @BindView(R.id.rv_index_list)
    RecyclerView rvIndexList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupElements();
        setListeners();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        mToolbar.setTitleTextColor(Utility.getColor(R.color.text_color));
    }

    private void setupElements() {
        setupToolbar();
        getCsvContents();
        ArrayAdapter adapter = new ArrayAdapter(mContext,
                android.R.layout.simple_spinner_dropdown_item, mListType);
        spListType.setAdapter(adapter);
        mAdapter = new HomeAdapter(mContext, mItems);
        rvItemList.setLayoutManager(new LinearLayoutManager(mContext));
        rvItemList.setAdapter(mAdapter);
        for (String item : (String[]) mAdapter.getSections()) {
            mIndexList.add(item);
        }
        mIndexAdapter = new IndexAdapter(mContext, mIndexList, new ValueCallback() {
            @Override
            public void call(int value) {
                int position = mAdapter.getPositionForSection(value);
                rvItemList.scrollToPosition(position);
            }
        });
        rvIndexList.setLayoutManager(new LinearLayoutManager(mContext));
        rvIndexList.setAdapter(mIndexAdapter);
    }

    private void setListeners() {
        spListType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSearchList(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
        ivMenuSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchView(true);
            }
        });
        ivMenuCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideKeyboard(etSearchText, true);
                setupSearchView(false);
                getSearchList("");
            }
        });
        etSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Utility.hideKeyboard(etSearchText, false);
                    String text = etSearchText.getText().toString().trim().toLowerCase();
                    if (!Utility.validateString(text)) {
                        Utility.showMessage(R.string.msg_search_empty, rootView);
                    } else {
                        getSearchList(text);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void showNameDetails(NameItem item) {
        mDialog = (NameDialog) NameDialog.instantiate(mContext, NameDialog.class.getName());
        mDialog.setDialogContent(mContext, item);
        mDialog.show(getSupportFragmentManager(), NameDialog.class.getSimpleName());
    }

    private void getCsvContents() {
        String fileName = "sample_data.csv";
        try {
            InputStream csvStream = getAssets().open(fileName);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            List<String[]> data = csvReader.readAll();
            //Skip the header of the file
            for (int i = 1; i < data.size(); i++) {
                String[] item = data.get(i);
                NameItem.GENDER gender = NameItem.GENDER.valueOf(item[2]);
                mList.add(new NameItem(item[0], item[1], gender));
            }
            mItems.addAll(mList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSearchList(int type) {
        mType = type;
        mItems.clear();
        if (mType != 0) {
            for (NameItem item : mList) {
                if (item.isType(mType)) {
                    mItems.add(item);
                }
            }
        } else {
            mItems.addAll(mList);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void getSearchList(String keyword) {
        mItems.clear();
        if (Utility.validateString(keyword)) {
            for (NameItem item : mList) {
                if (item.getName().toLowerCase().contains(keyword) && item.isType(mType)) {
                    mItems.add(item);
                }
            }
        } else {
            for (NameItem item : mList) {
                if (item.isType(mType)) {
                    mItems.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    private void setupSearchView(final boolean show) {
        isSearchView = show;
        int startPoint, endPoint;
        int radius = PixelUtil.getDisplayInfo().x;
        int xPoint = PixelUtil.getDisplayInfo().x;
        if (show) {
            startPoint = 0;
            endPoint = radius;
            showSearchView(true);
        } else {
            startPoint = radius;
            endPoint = 0;
            if (Utility.isLollipop()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showSearchView(false);
                    }
                }, ANIMATION_DURATION);
            } else {
                showSearchView(true);
            }
        }
        if (Utility.isLollipop()) {
            Animator animator = ViewAnimationUtils.createCircularReveal(rlSearchView,
                    xPoint, 0, startPoint, endPoint);
            animator.setDuration(ANIMATION_DURATION);
            animator.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setKeyboard(show);
                }
            }, ANIMATION_DURATION);
        } else {
            setKeyboard(show);
        }
    }

    private void showSearchView(boolean show) {
        int visible = show ? View.VISIBLE : View.GONE;
        rlSearchView.setVisibility(visible);
    }

    private void setKeyboard(boolean show) {
        if (show) {
            Utility.showKeyboard(etSearchText);
        } else {
            Utility.hideKeyboard(etSearchText, true);
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchView) {
            showSearchView(false);
            getSearchList("");
        } else {
            super.onBackPressed();
        }
    }
}
