package com.plusonelabs.calendar.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.plusonelabs.calendar.R;
import com.plusonelabs.calendar.RemoteViewsUtil;

import static com.plusonelabs.calendar.RemoteViewsUtil.*;

/**
 * @author yvolk@yurivolkov.com
 */
public enum EventEntryLayout {
    DEFAULT(R.layout.event_entry, "DEFAULT", R.string.default_multiline_layout) {
        @Override
        protected void setEventDetails(CalendarEntry entry, RemoteViews rv) {
            String eventDetails = entry.getEventTimeString() + entry.getLocationString();
            if (TextUtils.isEmpty(eventDetails)) {
                rv.setViewVisibility(R.id.event_entry_details, View.GONE);
            } else {
                rv.setViewVisibility(R.id.event_entry_details, View.VISIBLE);
                rv.setTextViewText(R.id.event_entry_details, eventDetails);
                setTextSize(entry.getSettings(), rv, R.id.event_entry_details, R.dimen.event_entry_details);
                setTextColorFromAttr(entry.getSettings().getEntryThemeContext(), rv, R.id.event_entry_details,
                        R.attr.eventEntryDetails);
            }
        }
    },
    ONE_LINE(R.layout.event_entry_one_line, "ONE_LINE", R.string.single_line_layout) {
        @Override
        protected String getTitleString(CalendarEntry event) {
            return event.getTitle() + event.getLocationString();
        }

        @Override
        protected void setEventDate(CalendarEntry entry, RemoteViews rv) {
            if (entry.getSettings().getShowDayHeaders()) {
                rv.setViewVisibility(R.id.event_entry_date_time, View.GONE);
            } else {
                rv.setViewVisibility(R.id.event_entry_date_time, View.VISIBLE);
                rv.setTextViewText(R.id.event_entry_date_time, entry.getSimpleEventTimeString());
                setTextSize(entry.getSettings(), rv, R.id.event_entry_date_time, R.dimen.event_entry_date_time);
            }
        }

        @Override
        protected void setEventTime(CalendarEntry entry, RemoteViews rv) {
        }
    };

    @LayoutRes
    public final int layoutId;
    public final String value;
    @StringRes
    public final int summaryResId;

    EventEntryLayout(@LayoutRes int layoutId, String value, int summaryResId) {
        this.layoutId = layoutId;
        this.value = value;
        this.summaryResId = summaryResId;
    }

    public static EventEntryLayout fromValue(String value) {
        EventEntryLayout layout = DEFAULT;
        for (EventEntryLayout item : EventEntryLayout.values()) {
            if (item.value.equals(value)) {
                layout = item;
                break;
            }
        }
        return layout;
    }

    public void visualizeEvent(CalendarEntry entry, RemoteViews rv) {
        setTitle(entry, rv);
        setEventDate(entry, rv);
        setEventTime(entry, rv);
        setEventDetails(entry, rv);
    }

    protected void setTitle(CalendarEntry event, RemoteViews rv) {
        rv.setTextViewText(R.id.event_entry_title, getTitleString(event));
        setTextSize(event.getSettings(), rv, R.id.event_entry_title, R.dimen.event_entry_title);
        setTextColorFromAttr(event.getSettings().getEntryThemeContext(), rv, R.id.event_entry_title, R.attr.eventEntryTitle);
        setMultiline(rv, R.id.event_entry_title, event.getSettings().isTitleMultiline());
    }

    protected String getTitleString(CalendarEntry event) {
        return event.getTitle();
    }

    protected void setEventDate(CalendarEntry entry, RemoteViews rv) {
        // Empty
    }

    protected void setEventTime(CalendarEntry entry, RemoteViews rv) {
        // Empty
    }

    protected void setEventDetails(CalendarEntry entry, RemoteViews rv) {
        // Empty
    }

}
