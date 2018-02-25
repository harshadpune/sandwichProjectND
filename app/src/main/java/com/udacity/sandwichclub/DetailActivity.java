package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDataBinding.originTv.setText(sandwich.getPlaceOfOrigin());
        mDataBinding.alsoKnownTv.setText(convertStringListToString(sandwich.getAlsoKnownAs()));
        mDataBinding.ingredientsTv.setText(convertStringListToString(sandwich.getIngredients()));
        mDataBinding.descriptionTv.setText(sandwich.getDescription());
        setUiVisibility(sandwich);

    }

    private void setUiVisibility(Sandwich sandwich) {
        if(TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            mDataBinding.originTv.setHeight(0);
            mDataBinding.tvOriginLabel.setHeight(0);
        }
        if(sandwich.getAlsoKnownAs().size() == 0) {
            mDataBinding.alsoKnownTv.setHeight(0);
            mDataBinding.tvAlsoKnownAsLabel.setHeight(0);
        }
        if(sandwich.getIngredients().size() == 0) {
            mDataBinding.ingredientsTv.setHeight(0);
            mDataBinding.tvIngredientsLabel.setHeight(0);
        }
        if(TextUtils.isEmpty(sandwich.getDescription())) {
            mDataBinding.descriptionTv.setHeight(0);
            mDataBinding.tvDescriptionLabel.setHeight(0);
        }
    }

    private String convertStringListToString(List<String> stringList){
        String convertedString = "";
        for (int i=0; i<stringList.size(); i++) {
            if(i==0)
                convertedString = stringList.get(i);
            else
                convertedString += ", "+stringList.get(i);
        }
        return convertedString;
    }
}
