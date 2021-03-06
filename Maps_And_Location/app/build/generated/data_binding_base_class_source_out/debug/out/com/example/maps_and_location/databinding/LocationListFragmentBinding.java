// Generated by view binder compiler. Do not edit!
package com.example.maps_and_location.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.maps_and_location.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LocationListFragmentBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final RecyclerView locationListRecyclerView;

  private LocationListFragmentBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout constraintLayout, @NonNull RecyclerView locationListRecyclerView) {
    this.rootView = rootView;
    this.constraintLayout = constraintLayout;
    this.locationListRecyclerView = locationListRecyclerView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LocationListFragmentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LocationListFragmentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.location_list_fragment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LocationListFragmentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout constraintLayout = (ConstraintLayout) rootView;

      id = R.id.location_list_recycler_view;
      RecyclerView locationListRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (locationListRecyclerView == null) {
        break missingId;
      }

      return new LocationListFragmentBinding((ConstraintLayout) rootView, constraintLayout,
          locationListRecyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
