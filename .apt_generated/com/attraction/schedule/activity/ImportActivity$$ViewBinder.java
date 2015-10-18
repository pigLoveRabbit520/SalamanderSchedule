// Generated code from Butter Knife. Do not modify!
package com.attraction.schedule.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ImportActivity$$ViewBinder<T extends com.attraction.schedule.activity.ImportActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361804, "field 'btnQueryLesson' and method 'onClick'");
    target.btnQueryLesson = finder.castView(view, 2131361804, "field 'btnQueryLesson'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361800, "field 'tvAccount'");
    target.tvAccount = finder.castView(view, 2131361800, "field 'tvAccount'");
    view = finder.findRequiredView(source, 2131361805, "field 'btnQueryGrade' and method 'onClick'");
    target.btnQueryGrade = finder.castView(view, 2131361805, "field 'btnQueryGrade'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361807, "field 'tvLog'");
    target.tvLog = finder.castView(view, 2131361807, "field 'tvLog'");
    view = finder.findRequiredView(source, 2131361802, "field 'tvPassword'");
    target.tvPassword = finder.castView(view, 2131361802, "field 'tvPassword'");
    view = finder.findRequiredView(source, 2131361803, "field 'btnLogin' and method 'onClick'");
    target.btnLogin = finder.castView(view, 2131361803, "field 'btnLogin'");
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
    target.btnQueryLesson = null;
    target.tvAccount = null;
    target.btnQueryGrade = null;
    target.tvLog = null;
    target.tvPassword = null;
    target.btnLogin = null;
  }
}
