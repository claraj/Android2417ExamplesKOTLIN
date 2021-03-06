// Generated by view binder compiler. Do not edit!
package com.example.treespotter_oop.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.treespotter_oop.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTreeDetailBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final SwitchCompat detailSpottedThisTree;

  @NonNull
  public final ImageView detailTreeIcon;

  @NonNull
  public final TextView state;

  @NonNull
  public final ConstraintLayout treeDetailContainer;

  @NonNull
  public final ImageView treeImage;

  @NonNull
  public final TextView treeName;

  private FragmentTreeDetailBinding(@NonNull ConstraintLayout rootView,
      @NonNull SwitchCompat detailSpottedThisTree, @NonNull ImageView detailTreeIcon,
      @NonNull TextView state, @NonNull ConstraintLayout treeDetailContainer,
      @NonNull ImageView treeImage, @NonNull TextView treeName) {
    this.rootView = rootView;
    this.detailSpottedThisTree = detailSpottedThisTree;
    this.detailTreeIcon = detailTreeIcon;
    this.state = state;
    this.treeDetailContainer = treeDetailContainer;
    this.treeImage = treeImage;
    this.treeName = treeName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTreeDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTreeDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_tree_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTreeDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.detail_spotted_this_tree;
      SwitchCompat detailSpottedThisTree = ViewBindings.findChildViewById(rootView, id);
      if (detailSpottedThisTree == null) {
        break missingId;
      }

      id = R.id.detail_tree_icon;
      ImageView detailTreeIcon = ViewBindings.findChildViewById(rootView, id);
      if (detailTreeIcon == null) {
        break missingId;
      }

      id = R.id.state;
      TextView state = ViewBindings.findChildViewById(rootView, id);
      if (state == null) {
        break missingId;
      }

      ConstraintLayout treeDetailContainer = (ConstraintLayout) rootView;

      id = R.id.tree_image;
      ImageView treeImage = ViewBindings.findChildViewById(rootView, id);
      if (treeImage == null) {
        break missingId;
      }

      id = R.id.tree_name;
      TextView treeName = ViewBindings.findChildViewById(rootView, id);
      if (treeName == null) {
        break missingId;
      }

      return new FragmentTreeDetailBinding((ConstraintLayout) rootView, detailSpottedThisTree,
          detailTreeIcon, state, treeDetailContainer, treeImage, treeName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
