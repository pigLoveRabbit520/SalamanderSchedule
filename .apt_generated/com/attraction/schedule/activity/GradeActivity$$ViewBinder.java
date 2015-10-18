// Generated code from Butter Knife. Do not modify!
package com.attraction.schedule.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GradeActivity$$ViewBinder<T extends com.attraction.schedule.activity.GradeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361794, "field 'spnYear'");
    target.spnYear = finder.castView(view, 2131361794, "field 'spnYear'");
    view = finder.findRequiredView(source, 2131361795, "field 'spnTerm'");
    target.spnTerm = finder.castView(view, 2131361795, "field 'spnTerm'");
    view = finder.findRequiredView(source, 2131361798, "field 'lvGrade'");
    target.lvGrade = finder.castView(view, 2131361798, "field 'lvGrade'");
    view = finder.findRequiredView(source, 2131361796, "field 'spnType'");
    target.spnType = finder.castView(view, 2131361796, "field 'spnType'");
    view = finder.findRequiredView(source, 2131361797, "field 'btnQuery' and method 'onClick'");
    target.btnQuery = finder.castView(view, 2131361797, "field 'btnQuery'");
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
    target.spnYear = null;
    target.spnTerm = null;
    target.lvGrade = null;
    target.spnType = null;
    target.btnQuery = null;
  }
}
