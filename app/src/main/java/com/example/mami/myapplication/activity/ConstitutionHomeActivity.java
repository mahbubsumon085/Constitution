package com.example.mami.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.adapter.CustomTreeAdapter;
import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.bean.AmmendMent;
import com.example.mami.myapplication.bean.ContentNode;
import com.example.mami.myapplication.database.DatabaseAccess;
import com.example.mami.myapplication.fragment.FragmentAmmendMent;
import com.example.mami.myapplication.fragment.FragmentArticle;
import com.example.mami.myapplication.fragment.FragmentNote;
import com.example.mami.myapplication.fragment.TreeViewDetailsFragment;
import com.example.mami.myapplication.utility.Utility;
import com.example.mami.myapplication.viewbinder.CustomViewPager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mami on 12/4/2017.
 */

public class ConstitutionHomeActivity extends AppCompatActivity{


    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
    ListView list_item;
    //ActionBar mActionBar;
 //   Toolbar toolbar;
    public FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public RelativeLayout layout_top, re_top;
    public TextView tv_mid_title, tv_back;
    ImageView im_search;
    //MenuItem home;
    RelativeLayout re_home;
    int from = 0;
    @SuppressLint("ValidFragment")
    //StickerAdapter adapter;
            ProgressBar pr_wait;
    ViewGroup v;

    int position=0;
    final int NO_OF_TAB=4;
    static int selectedtab=0;
    private CustomViewPager mStickerPager;
    LinearLayout ln_sticker_holder;
    String tabTitles[] = new String[] { "Details","Article",
            "Ammendment","Note"};

    List<CustomTreeNode> nodes;
    List<CustomTreeNode> artiCleNodes;
    DatabaseAccess databaseAccess;
    ArrayList<AmmendMent> ammendList;
    ArrayList<AmmendMent> englishAmmendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_home);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mStickerPager= (CustomViewPager) findViewById(R.id.home_pager);

        pr_wait=(ProgressBar)findViewById(R.id.pr_wait);
        pr_wait.bringToFront();
        ln_sticker_holder= (LinearLayout) findViewById(R.id.ln_sticker_holder);
     //   toolbar = (Toolbar) findViewById(R.id.toolbar);
        pr_wait= findViewById(R.id.pr_wait);

        ln_sticker_holder.setVisibility(View.VISIBLE);
        databaseAccess = DatabaseAccess.getInstance(this);
        if(databaseAccess!=null){
            databaseAccess.open();
        }
        else{
            Log.d("databaseAccess","Not found");
        }
        new DownloadFilesTask().execute(null, null, null);

    }
    private class DownloadFilesTask extends AsyncTask<URL, Integer, String> {
        protected String doInBackground(URL... urls) {
            List<ContentNode> nodeList = databaseAccess.getContentNode();
            ContentNode node=new ContentNode();
            node.setId(1001);
            node.setParentID(0);
            node.setColumnmBanglaTitle("বিস্‌মিল্লাহির-রহ্‌মানির রহিম");
            node.setColumnmTitle(" BISMILLAH-AR-RAHMAN-AR-RAHIM");
            node.setColumnBanglaMessage("১[(দয়াময়, পরম দয়ালু, আল্লাহের নামে)/\n" +
                    "\n" +
                    "পরম করুণাময় সৃষ্টিকর্তার নামে।]");
            node.setColumnMessage("(১[ In the name of Allah, the Beneficient, the Merciful)/\n" +
                    "\n" +
                    "In the name of the Creator, the Merciful.]");
            CustomTreeNode starter=new CustomTreeNode(node);
            nodes = Utility.generateTree(nodeList);
            nodes.add(0,starter);
            nodeList = databaseAccess.getContentNode();
            artiCleNodes=Utility.generateArticleTree(nodeList);
            ammendList=databaseAccess.getBanglaAmmendMent();
            String matcher="";
            for(int i=ammendList.size()-1; i>=0;i--){
                if(i<10){
                    matcher=matcher+ammendList.get(i).getAmmendMentString().substring(0,1)+"[|";
                }
                else if(i<100){
                    matcher=matcher+ammendList.get(i).getAmmendMentString().substring(0,2)+"[|";
                }
                else{
                    matcher=matcher+ammendList.get(i).getAmmendMentString().substring(0,3)+"[|";

                }
            }
            Log.d("Matcher","matcher   "+matcher);
            englishAmmendList=databaseAccess.getEnglishAmmendMent();
            return "";
        }
        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(String resul) {
            pr_wait.setVisibility(View.GONE);
            setPagerAdapterData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
        // R.anim.slide_in_right);

        super.onBackPressed();

    }







    private void notifyAdapter(){
        //Constants.debugLog("ACTION_STICKER_CHANGE_BASEDON_CATID","notifyAdapter:"+selectedtab);
        selectedtab=mStickerPager.getCurrentItem();


    }
    class PagerAdapter extends FragmentPagerAdapter {
        //String tabTitles[] = new String[] { getString(R.string.media_recent), getString(R.string.media_album), getString(R.string.media_downloads),getString(R.string.media_upload) };
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        public View getTabView(int position, Activity activity) {
            View tab = LayoutInflater.from(activity).inflate(R.layout.tab_bar_custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(fragmentTitleList.get(position));
            if(position < (fragmentList.size()-1)) {
                tab.findViewById(R.id.searchTabDivider).setVisibility(View.VISIBLE);
            } else {
                tab.findViewById(R.id.searchTabDivider).setVisibility(View.GONE);
            }
            return tab;
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

    }

   PagerAdapter pagerAdapter;

    private void setPagerAdapterData(){

        detailsFragment=new TreeViewDetailsFragment();
        detailsFragment.setData(nodes,databaseAccess,ammendList,englishAmmendList);
        articleFrament=new FragmentArticle();
        articleFrament.setData(artiCleNodes,databaseAccess,ammendList,englishAmmendList);
        ammendmentFagment=new FragmentAmmendMent();
        ammendmentFagment.setAmmendList(ammendList,englishAmmendList);
        noteFagment=new FragmentNote();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(detailsFragment, tabTitles[0]);
        pagerAdapter.addFragment(articleFrament, tabTitles[1]);
        pagerAdapter.addFragment(ammendmentFagment, tabTitles[2]);
        pagerAdapter.addFragment(noteFagment, tabTitles[3]);
        mStickerPager.setAdapter(pagerAdapter);
        mStickerPager.setCurrentItem(from);
        mStickerPager.setOffscreenPageLimit(NO_OF_TAB);
        tabLayout.setupWithViewPager(mStickerPager);
        for(int j=0;j<tabLayout.getTabCount();j++){
            TabLayout.Tab tab = tabLayout.getTabAt(j);
            tab.setCustomView(pagerAdapter.getTabView(j, ConstitutionHomeActivity.this));
            //tab.select();
        }
        mStickerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                notifyAdapter();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
    private TabLayout tabLayout;
    TreeViewDetailsFragment detailsFragment;
    FragmentArticle articleFrament;
    FragmentAmmendMent ammendmentFagment;
    FragmentNote noteFagment;

}
