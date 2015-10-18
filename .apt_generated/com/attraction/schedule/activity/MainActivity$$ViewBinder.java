// Generated code from Butter Knife. Do not modify!
package com.attraction.schedule.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.attraction.schedule.activity.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361810, "field 'timetable'");
    target.timetable = finder.castView(view, 2131361810, "field 'timetable'");
    view = finder.findRequiredView(source, 2131361808, "field 'tvSettings' and method 'onClick'");
    target.tvSettings = finder.castView(view, 2131361808, "field 'tvSettings'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.timetable = null;
    target.tvSettings = null;
  }
}
