package com.bealink.zhengwuy.bealink.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.adapter.ContactsListAdapter;
import com.bealink.zhengwuy.bealink.bean.other.ContactBean;
import com.bealink.zhengwuy.bealink.utils.ContactsUtil;
import com.bealink.zhengwuy.bealink.view.WordsList;

import java.util.Collections;
import java.util.List;

public class ContactsFragment extends Fragment implements WordsList.OnIndexChangeListener{
    private WordsList mWordsList;
    private TextView mWordTv;
    private Handler mHandler;
    private RecyclerView mContactsRv;
    private ContactsListAdapter mContactsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<ContactBean> mContactBeanList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initView(View view) {
        mWordsList = view.findViewById(R.id.word_list);
        mWordsList.setOnIndexChangeListener(this);
        mWordTv = view.findViewById(R.id.word_tv);
        mContactsRv = view.findViewById(R.id.contact_recycler);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContactsRv.setLayoutManager(mLinearLayoutManager);
    }

    private void initListener() {
        mContactsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = linearLayoutManager.findFirstVisibleItemPosition();
                mWordsList.setCurrentIndex(mContactBeanList.get(position).getHeaderWord());
            }
        });
    }

    private void initData() {
        mContactBeanList = new ContactsUtil(getContext()).getPhone();
//        Collections.sort(mContactBeanList, (contactBean, t1) -> contactBean.getPinyin().compareTo(t1.getPinyin()));
        Collections.sort(mContactBeanList, (o1, o2) -> {
            if (o1.getHeaderWord().equals("#")) {
                return 1;
            } else if (o2.getHeaderWord().equals("#")) {
                return -1;
            }
            return o1.getPinyin().compareTo(o2.getPinyin());
        });
        mContactsListAdapter = new ContactsListAdapter(mContactBeanList);
        mContactsRv.setAdapter(mContactsListAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onIndexChangeListener(String headerWord) {
        mWordTv.setText(headerWord);
        mWordTv.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(() -> mWordTv.setVisibility(View.GONE), 300);

        int position = mContactsListAdapter.getFirstPosition(headerWord);
        if (position != -1) {
            mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
            mLinearLayoutManager.setStackFromEnd(true);
        }
    }
}
