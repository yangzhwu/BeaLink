package com.bealink.zhengwuy.bealink.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.Comparator;
import java.util.List;

public class ContactsFragment extends Fragment implements WordsList.OnIndexChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WordsList mWordsList;
    private TextView mWordTv;
    private Handler mHandler;
    private RecyclerView mContactsRv;
    private ContactsListAdapter mContactsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

    }

    private void initData() {
        List<ContactBean> contactsList = new ContactsUtil(getContext()).getPhone();
        Collections.sort(contactsList, (contactBean, t1) -> contactBean.getPinyin().compareTo(t1.getPinyin()));
        mContactsListAdapter = new ContactsListAdapter(contactsList);
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
