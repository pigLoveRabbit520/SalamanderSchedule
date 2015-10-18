// Generated code from Butter Knife. Do not modify!
package com.attraction.schedule.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Timetable$$ViewBinder<T extends com.attraction.schedule.view.Timetable> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361826, "field 'svContainer'");
    target.svContainer = finder.castView(view, 2131361826, "field 'svContainer'");
    view = finder.findRequiredView(source, 2131361824, "field 'monthView'");
    target.monthView = finder.castView(view, 2131361824, "field 'monthView'");
    view = finder.findRequiredView(source, 2131361825, "field 'llMonthWeekDayViewContainer'");
    target.llMonthWeekDayViewContainer = finder.castView(view, 2131361825, "field 'llMonthWeekDayViewContainer'");
    view = finder.findRequiredView(source, 2131361827, "field 'llClassLessonContainer'");
    target.llClassLessonContainer = finder.castView(view, 2131361827, "field 'llClassLessonContainer'");
  }

  @Override public void unbind(T target) {
    target.svContainer = null;
    target.monthView = null;
    target.llMonthWeekDayViewContainer = null;
    target.llClassLessonContainer = null;
  }
}
