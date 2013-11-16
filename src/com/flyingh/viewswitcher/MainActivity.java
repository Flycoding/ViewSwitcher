package com.flyingh.viewswitcher;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity {
	private static final int PER_SCREEN_APP_NUMBER = 20;
	private ViewSwitcher viewSwitcher;
	private ListAdapter adapter;
	private int screenNo;
	private int screenCount;

	class App {

		public App(Drawable icon, String label) {
			super();
			this.icon = icon;
			this.label = label;
		}

		private Drawable icon;
		private String label;

		public Drawable getIcon() {
			return icon;
		}

		public void setIcon(Drawable icon) {
			this.icon = icon;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);
		viewSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {
				return View.inflate(MainActivity.this, R.layout.screen_item, null);
			}
		});
		final List<App> apps = new ArrayList<App>();
		for (int i = 0; i < 85; i++) {
			apps.add(new App(getResources().getDrawable(R.drawable.ic_launcher), String.valueOf(i)));
		}
		screenCount = apps.size() % PER_SCREEN_APP_NUMBER == 0 ? apps.size() / PER_SCREEN_APP_NUMBER : apps.size() / PER_SCREEN_APP_NUMBER + 1;
		adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = View.inflate(MainActivity.this, R.layout.app_item, null);
				ImageView icon = (ImageView) view.findViewById(R.id.icon);
				TextView label = (TextView) view.findViewById(R.id.label);
				App app = (App) getItem(position);
				icon.setImageDrawable(app.getIcon());
				label.setText(app.getLabel());
				return view;
			}

			@Override
			public long getItemId(int position) {
				return screenNo * PER_SCREEN_APP_NUMBER + position;
			}

			@Override
			public Object getItem(int position) {
				return apps.get((int) getItemId(position));
			}

			@Override
			public int getCount() {
				if (screenNo == screenCount - 1 && apps.size() % PER_SCREEN_APP_NUMBER != 0) {
					return apps.size() % PER_SCREEN_APP_NUMBER;
				}
				return PER_SCREEN_APP_NUMBER;
			}
		};
	}

	public void showPrevious(View view) {
		if (screenNo > 0) {
			--screenNo;
			GridView gridView = (GridView) viewSwitcher.getNextView();
			viewSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
			viewSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
			gridView.setAdapter(adapter);
			viewSwitcher.showPrevious();
		}

	}

	public void showNext(View view) {
		if (screenNo < screenCount - 1) {
			++screenNo;
			GridView gridView = (GridView) viewSwitcher.getNextView();
			gridView.setAdapter(adapter);
			viewSwitcher.showNext();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
