/**
 * @file MainActivity.java
 * The main activity that will be shown when user start up
 * Three basic framents navigated by tabs
 * Using the library of ActionBarSherlock
 */
package hkust.comp3111h.focus.Activity;

import java.util.List;
import java.util.Vector;

import hkust.comp3111h.focus.R;
import hkust.comp3111h.focus.ui.PagerAdapter;
import hkust.comp3111h.focus.ui.StatisticsFragment;
import hkust.comp3111h.focus.ui.TaskManageFragment;
import hkust.comp3111h.focus.ui.TimerFragment;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.ActionBar;
import android.support.v4.app.ActionBar.OnNavigationListener;
import android.support.v4.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.support.v4.view.ViewPager;

import android.view.View;

import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;


public class MainActivity extends FragmentActivity 
  implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
  //determine honecome or not
  static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

  private final Handler handler = new Handler();
  private ViewPager mViewPager;
  private PagerAdapter mPagerAdapter;

  //Actionbar set up
  private boolean useLogo = false;
  private boolean showHomeUp = false;

  /** 
   * Called when the activity is first created 
   * Initialize Environment
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pagerlayout); //Basically a easy linear layout
    final ActionBar ab = getSupportActionBar();
    //Initialise ActionBar
    //Set defaults for logo and home up
    ab.setDisplayHomeAsUpEnabled(showHomeUp);
    ab.setDisplayUseLogoEnabled(useLogo);
    ab.setDisplayShowTitleEnabled(false);

    //Set up tabs navigation
    ab.addTab(ab.newTab().setText(R.string.tab1_name).setTabListener(this));
    ab.addTab(ab.newTab().setText(R.string.tab2_name).setTabListener(this));
    ab.addTab(ab.newTab().setText(R.string.tab3_name).setTabListener(this));
    ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    //Initialise ViewPager
    this.initialiseViewPager();
  }

  /**
   * Initialize ViewPager
   */
  
  private void initialiseViewPager() {
    List<Fragment> fragments = new Vector<Fragment>();
    fragments.add(Fragment.instantiate(this, TaskManageFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, TimerFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, StatisticsFragment.class.getName()));
    this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
    this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
    this.mViewPager.setAdapter(this.mPagerAdapter);
    this.mViewPager.setOnPageChangeListener(this);
  }
  /**
   * Creating the action bar menu, redundent currently
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override 
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }


  public void onTabReselected(Tab tab, FragmentTransaction ft) {
// TODO Auto-generated method stub
  }	
	
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
// TODO Auto-generated method stub
	if(mViewPager==null){
	  this.initialiseViewPager();
	}
	mViewPager.setCurrentItem(tab.getPosition());
  }
	
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
  // TODO Auto-generated method stub
  }

public void onPageScrolled(int position, float positionOffset,
		int positionOffsetPixels) {
	// TODO Auto-generated method stub
	
}

public void onPageSelected(int position) {
	// TODO Auto-generated method stub
    final ActionBar ab = getSupportActionBar();
    ab.setSelectedNavigationItem(position);
	
}

public void onPageScrollStateChanged(int state) {
	// TODO Auto-generated method stub
	
}
  
}