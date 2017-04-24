package ru.dron2004.translateapp.ui.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ru.dron2004.translateapp.R;
import ru.dron2004.translateapp.model.Translation;
import ru.dron2004.translateapp.utility.WidgetUtils;

public class DetailActivity extends AppCompatActivity {
    private TextView translationText,originalText;
    private Translation translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        translationText = (TextView) findViewById(R.id.translationText);
        translationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        originalText = (TextView) findViewById(R.id.originalText);
        Intent i = getIntent();
        if (i.hasExtra("TRANSLATION")) {
            translation = (Translation) i.getSerializableExtra("TRANSLATION");
            translationText.setText(translation.translatedText);
            originalText.setText(translation.textToTranslate);

            WidgetUtils.fitText(
                    translationText,
                    translation.translatedText,
                    getResources().getDimensionPixelSize(R.dimen.max_text_size),
                    getResources().getDimensionPixelSize(R.dimen.max_text_width)
                    );
        } else {
            finish();
        }
    }
}
