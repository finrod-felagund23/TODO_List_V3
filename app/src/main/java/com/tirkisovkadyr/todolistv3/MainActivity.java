package com.tirkisovkadyr.todolistv3;
// javac -classpath .;.\libs\*; ch01\sec01\jacksonWorks\Main.java && java -classpath libs\*; ch01.sec01.jacksonWorks.Main

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;

import kotlin.jvm.internal.Lambda;


public class MainActivity extends AppCompatActivity {
    private final String FILE_NAME = "content.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false);
//        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        File file = new File(getApplicationContext().getFilesDir(), FILE_NAME);
        if (!file.exists()) {
            try {
                openFileOutput(FILE_NAME, MODE_PRIVATE).close();
            } catch (FileNotFoundException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                ex.printStackTrace();
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    HashMap<String, TODO> placeHolderMap = new HashMap<>();
                    String json = objectWriter.writeValueAsString(placeHolderMap);
                    fos.write(json.getBytes(StandardCharsets.UTF_8));
                } catch (JsonProcessingException | FileNotFoundException ex1) {
                    Toast.makeText(this, "JSON + "+ex1.getMessage(), Toast.LENGTH_LONG).show();
                    ex1.printStackTrace();
                } catch (IOException ex1 ) {
                    Toast.makeText(this, "IOE + "+ex1.getMessage(), Toast.LENGTH_LONG).show();
                    ex1.printStackTrace();
                }
            } catch (IOException ex2) {
                Toast.makeText(this, ex2.getMessage(), Toast.LENGTH_LONG).show();
                ex2.printStackTrace();
            }
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int screenWidth = displayMetrics.widthPixels;
//        int screenHeight = displayMetrics.heightPixels;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
//        int screenHeight = size.x;
//        int screenWidth = size.y;


//        screenWidth = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, displayMetrics.widthPixels, getResources().getDisplayMetrics()
//        );
//        screenHeight = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, displayMetrics.heightPixels, getResources().getDisplayMetrics()
//        );

        System.out.println((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,  600, getResources().getDisplayMetrics()
        ));
        System.out.println((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, 600, getResources().getDisplayMetrics()
        ));
        System.out.println((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 600, getResources().getDisplayMetrics()
        ));

//        System.out.println(screenHeight);
//        System.out.println(screenWidth);
        System.out.println(size.y);
        System.out.println(size.x);

        ConstraintLayout constraintLayout = findViewById(R.id.main_constraint_layout);

        if (constraintLayout!= null) {
            System.out.println("Its not nu;l;l");
//            constraintLayout.setMinHeight(screenHeight);
////            constraintLayout.setMinWidth(screenWidth);
//            constraintLayout.setMinWidth(size.x);
//            System.out.println(size.x - 20);
//            System.out.println(size.y - 20);
            constraintLayout.setMinHeight(size.y - 160); // 1120 for SMJ5-2016

            System.out.println("91");
        } else {
            System.out.println("ConstraintLayout constraintLayout = findViewById(R.id.main_constraint_layout); IS NULL");
        }
        Button addBtn = findViewById(R.id.add_btn);

        addBtn.setOnClickListener(this::onClick);

        ConstraintLayout.LayoutParams layoutParamsForThemeTxt = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParamsForThemeTxt.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForThemeTxt.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForThemeTxt.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForThemeTxt.setMargins(0, 10, 0, 0);

        ConstraintLayout.LayoutParams layoutParamsForDescriptionTxt = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParamsForDescriptionTxt.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForDescriptionTxt.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForDescriptionTxt.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParamsForDescriptionTxt.setMargins(0, 15, 0, 20);

        constraintLayout.removeView(findViewById(R.id.havent_todo_txt));
        int idOfTopView = 0;
        int counter = 0;
        System.out.println("103");
        HashMap<String, TODO> todoHashMap = this.getTodoFromFile();
        System.out.println(105);
        System.out.println(Objects.nonNull(todoHashMap));
        if (Objects.nonNull(todoHashMap)) {
            ConstraintLayout.LayoutParams layoutParams = null;
            ConstraintLayout constraintLayout1 = null;

            for (String key : todoHashMap.keySet()) { // Error in that place
                constraintLayout1 = new ConstraintLayout(this);
//                constraintLayout1.setLayoutParams(constraintLayout1Params);
                // keys saved in String but used like Integer
                System.out.println("109");
                if (counter == 0) {
                    counter++;

                    layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, 100);
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                    layoutParams.setMargins(5, 40, 5, 0);
                } else {
                    layoutParams = new ConstraintLayout.LayoutParams
                            (ConstraintLayout.LayoutParams.MATCH_PARENT, 100);
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    layoutParams.topToBottom = idOfTopView;
                    layoutParams.setMargins(5, 40, 5, 0);
                }
                constraintLayout1.setId(View.generateViewId());
                idOfTopView = constraintLayout1.getId();

                TextView txtTheme = new TextView(this);
                txtTheme.setLayoutParams(layoutParamsForThemeTxt);
                txtTheme.setId(View.generateViewId());
                txtTheme.setText(todoHashMap.get(key).getTheme());
                txtTheme.setTextSize(20);

                TextView txtDescription = new TextView(this);
                txtDescription.setLayoutParams(layoutParamsForDescriptionTxt);
                txtDescription.setId(View.generateViewId());
                txtDescription.setText(todoHashMap.get(key).getDescription());

                constraintLayout1.setLayoutParams(layoutParams);

                constraintLayout1.setBackgroundResource(R.drawable.layout_border);

                constraintLayout1.addView(txtTheme);
                constraintLayout1.addView(txtDescription);

                constraintLayout.addView(constraintLayout1);

                System.out.println("129");
                System.out.println(todoHashMap);
//                        String.format
//                                (
//                                        "%s \n\n%s",
//                                        todoHashMap.get(key).getTheme(),
//                                        todoHashMap.get(key).getDescription()
//                                )
//                );

                System.out.println("141");
            }
            System.out.println(143);

        }
        System.out.println(146);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, MakeNewTodoActivity.class);
        startActivity(intent);
    }

    public void onClickReadFromFile(View view) {
        FileInputStream fin = null;
        TextView txt = findViewById(R.id.havent_todo_txt);

        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            txt.setText(text);
        }
        catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch ( IOException ex ) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    protected HashMap<String, TODO> getTodoFromFile() {
        System.out.println("181");
        FileInputStream fin = null;

        HashMap<String, TODO> myMap = new HashMap<>();
        myMap.put("0", new TODO("SOMETHING", "DESC"));



        HashMap<String, TODO> todoHashMap = null;
        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            System.out.println(text);
            todoHashMap = objectMapper.readValue(text, new TypeReference<HashMap<String, TODO>>(){});
            System.out.println(todoHashMap);
            try {
                System.out.println(Objects.requireNonNull(todoHashMap.get("0")).getDescription());
                System.out.println(todoHashMap.get("0").getTheme());
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            System.out.println("196");
        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        } finally {
            System.out.println(203);
            try {
                if (fin != null) {
                    System.out.println(206);
                    fin.close();
                }

            }
            catch ( IOException ex ) {
                System.out.println(212);
                ex.printStackTrace();
            }
        }
//        File file = new File(".", "some.json");
//        FileWriter fileWriter = new FileWriter(file);
        System.out.println(218);
        return todoHashMap;
    }
}



/*
@Test
void givenJsonString_whenDeserializingWithTypeReference_thenGetExpectedList()
  throws JsonProcessingException {
    String jsonString = readFile("/to-java-collection/books.json");
    List<Book> bookList = objectMapper.readValue(jsonString, new TypeReference<List<Book>>() {});
    assertThat(bookList.get(0)).isInstanceOf(Book.class);
    assertThat(bookList).isEqualTo(expectedBookList);
}
 */


