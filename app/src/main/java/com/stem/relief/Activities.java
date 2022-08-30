package com.stem.relief;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.transition.Transition;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import static java.lang.Thread.sleep;

public class Activities extends Activity {

    Button age_range1, age_range2, age_range3;
    TextView ask_age;
    ExpandingList expandingList;
    Transition transition = new Fade();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_layout); //Layout to JAVA linking

        ask_age = findViewById(R.id.ask_age_text); //View to JAVA Linking
        age_range1 = findViewById(R.id.age_range1);

        expandingList = (ExpandingList) findViewById(R.id.expanding_list_main);


        //On Click listener for (12 - 18) years range to make the first List

        age_range1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappear(); //To hide the age ranges buttons and show the list selected

                //1st pose
                ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item.findViewById(R.id.title)).setText("Bow Pose");
                item.setIndicatorIconRes(R.drawable.yoga_bow);
                item.setIndicatorColor(Color.parseColor("#E7E7E7"));

                //This will create 1 sub item
                item.createSubItems(1);

                //get a sub item View
                View subItem = item.getSubItemView(0);
                ((TextView) subItem.findViewById(R.id.sub_title))
                        .setText("Step 1: Lie on your belly with your hands alongside your torso, palms up. \n\n" +
                                "Step 2: Exhale and bend your knees, bringing your heels as close as you can to your buttocks. \n\n" +
                                "Step 3: Strongly lift your heels away from your buttocks and lift your thighs away from the floor. \n\n" +
                                "Step 4: Press your shoulder blades firmly against your back to open your heart. Draw the tops of the shoulders away from your ears. Sure, not to stop breathing.\n");
                ((ImageView) subItem.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_bow);


                //2nd pose
                ExpandingItem item2 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item2.findViewById(R.id.title)).setText("Bridge Pose");
                item2.setIndicatorIconRes(R.drawable.yoga_bridge);
                item2.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item2.createSubItems(1);

                final View subItem2 = item2.getSubItemView(0);
                ((TextView) subItem2.findViewById(R.id.sub_title))
                        .setText("Step 1: Lie on your back with your knees bent and feet on the floor. Extend your arms along the floor. \n\n" +
                                "Step 2: Press your feet and arms firmly into the floor. Draw your tailbone toward your pubic bone, holding your buttocks off the floor. \n\n" +
                                "Step 3: Roll your shoulders back and underneath your body.\n\n" +
                                "Step 4: Clasp your hands and extend your arms along the floor beneath your pelvis. Press your weight evenly across all four corners of both feet. Lengthen your tailbone toward the backs of your knees. \n\n" +
                                "Hold for up to one minute.\n");
                ((ImageView) subItem2.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_bridge);

                //mark timer

                //3rd pose
                ExpandingItem item3 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item3.findViewById(R.id.title)).setText("Happy Baby Pose");
                item3.setIndicatorIconRes(R.drawable.yoga_happy_baby);
                item3.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item3.createSubItems(1);

                View subItem3 = item3.getSubItemView(0);
                ((TextView) subItem3.findViewById(R.id.sub_title))
                        .setText("Step 1: Lie on your back. With an exhale, bend your knees into your belly. \n\n" +
                                "Step 2:Grip the outsides of your feet with your hands. Open your knees slightly wider than your torso, then bring them up toward your armpits. \n\n" +
                                "Step 3: Position each ankle directly over the knee, so your shins are perpendicular to the floor. Flex through the heels. Gently push your feet up into your hands as you pull your hands down to create a resistance.\n");
                ((ImageView) subItem3.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_happy_baby);
            }
        });


        age_range2 = findViewById(R.id.age_range2);

        //On Click listener for (19 - 30) years range to make the first List
        age_range2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappear();


                //1st pose
                ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item.findViewById(R.id.title)).setText("Easy Pose");
                item.setIndicatorIconRes(R.drawable.yoga_easy);
                item.setIndicatorColor(Color.parseColor("#E7E7E7"));

                //This will create 1 sub item
                item.createSubItems(1);

                //get a sub item View
                View subItem = item.getSubItemView(0);
                ((TextView) subItem.findViewById(R.id.sub_title))
                        .setText("Step 1: Sit on the edge of a firm blanket. Extend your legs in front of your body.\n\n" +
                                "Step 2: With your knees wide, place each foot beneath the opposite knee. Fold your legs in toward your torso. Place your hands on your knees, palms down.\n\n" +
                                "Step 3: Balance your weight evenly across your sit bones. Align your head, neck, and spine. Lengthen your spine, but soften your neck. Relax your feet and thighs.\n\n" +
                                "Step 4: Hold for up to one minute or for the duration of your meditation or pranayama practice.\n");
                ((ImageView) subItem.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_easy);


                //2nd pose
                ExpandingItem item2 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item2.findViewById(R.id.title)).setText("Standing Forward Bend");
                item2.setIndicatorIconRes(R.drawable.yoga_forward_bend);
                item2.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item2.createSubItems(1);

                View subItem2 = item2.getSubItemView(0);
                ((TextView) subItem2.findViewById(R.id.sub_title))
                        .setText("Step 1: use your front thigh muscles to actively pull your kneecaps up toward your hips. With your fingers interlaced and your arms behind your back, lift your arms any amount away from your back. \n\n" +
                                "Step 2: Hold for 5 breaths, then change the interlace by putting the other index finger on top and stay for another 5 breaths. \n\n" +
                                "Step 3: Take your hands to your hips, and your thumbs to the top of your behind. Drop the flesh of your buttocks to the floor to propel you up to stand. Take a giant step out to the right.\n");
                ((ImageView) subItem2.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_forward_bend);


                //3rd pose
                ExpandingItem item3 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item3.findViewById(R.id.title)).setText("Rabbit Pose");
                item3.setIndicatorIconRes(R.drawable.yoga_rabbit);
                item3.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item3.createSubItems(1);

                View subItem3 = item3.getSubItemView(0);
                ((TextView) subItem3.findViewById(R.id.sub_title))
                        .setText("Step 1: From Child's Pose, interlace your fingers behind your back, lift your hips, and roll to the crown of your head. Keep pressing the tops of your feet down, so that you can control the amount of weight on your head.\n\n" +
                                "Step 2: Take your hands any amount away from your back. Lower down, change the interlace, lift your hips, and roll to the crown of head again.\n\n" +
                                "Step 3: Lift and lower 3 times on each side, changing the interlace each time. Create a rhythm with the breath and movement.\n");
                ((ImageView) subItem3.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_rabbit);

            }
        });

        age_range3 = findViewById(R.id.age_range3);

        //On Click listener for (31 - 50) years range to make the first List
        age_range3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disappear();

                //1st pose
                ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item.findViewById(R.id.title)).setText("Chair Pose");
                item.setIndicatorIconRes(R.drawable.yoga_chair);
                item.setIndicatorColor(Color.parseColor("#E7E7E7"));

                //This will create 1 sub item
                item.createSubItems(1);

                //get a sub item View
                View subItem = item.getSubItemView(0);
                ((TextView) subItem.findViewById(R.id.sub_title))
                        .setText("Step 1: the knees are hips-width apart, the knees are bent forward. and keep your hand on thighs.\n" +
                                "\n" +
                                "Step 2: keep the hips back, the chest is forward, and pull the abdomen in and up.\n" +
                                "\n" +
                                "Step 3: keep the arms above the head, in line with the ears, and keep spine long.\n");
                ((ImageView) subItem.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_chair);


                //2nd pose
                ExpandingItem item2 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item2.findViewById(R.id.title)).setText("Camel Pose");
                item2.setIndicatorIconRes(R.drawable.yoga_camel);
                item2.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item2.createSubItems(1);

                View subItem2 = item2.getSubItemView(0);
                ((TextView) subItem2.findViewById(R.id.sub_title))
                        .setText("Step 1: Begin by kneeling upright with your knees hip-distance apart. Rotate your thighs inward and press your shins and the tops of your feet into the floor.\n" +
                                " \n" +
                                "Step 2: Bend your shoulder backward and reach back and hold onto each heel. Your palms should rest on soles of feet.\n" +
                                "\n" +
                                "Step 3: Keep your thighs perpendicular to the floor, with your hips directly over your knees. \n" +
                                "\n" +
                                "Step 4: Lift up through your pelvis, keeping your lower spine long. Turn your arms outward . Keep your head in a neutral position.\n" +
                                "\n" +
                                "Hold for 30-60 seconds.\n");
                ((ImageView) subItem2.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_camel);


                //3rd pose
                ExpandingItem item3 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item3.findViewById(R.id.title)).setText("Downward-Facing Dog");
                item3.setIndicatorIconRes(R.drawable.yoga_downward_dog);
                item3.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item3.createSubItems(1);

                View subItem3 = item3.getSubItemView(0);
                ((TextView) subItem3.findViewById(R.id.sub_title))
                        .setText("Step 1: Place knees directly below hips and hands slightly forward of the shoulders.\n" +
                                "\n" +
                                "Step 2: keep outside edges of the feet parallel and push knee backwards.\n" +
                                "\n" +
                                "Step 3: press your hands down and spread fingers wide.\n" +
                                "\n" +
                                "Step 4: push chest back towards thighs and lengthen sitting bones to sky.\n" +
                                "\n" +
                                "Step 5: Stay in the pose anywhere from 1 to 3 minutes.\n");
                ((ImageView) subItem3.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_downward_dog);

                //4th pose
                ExpandingItem item4 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item4.findViewById(R.id.title)).setText("Child pose");
                item4.setIndicatorIconRes(R.drawable.yoga_child_traditional);
                item4.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item4.createSubItems(1);

                View subItem4 = item4.getSubItemView(0);
                ((TextView) subItem4.findViewById(R.id.sub_title))
                        .setText("Step 1: Spread your knees wide apart while keeping your big toes touching. Rest your buttocks on your heels.\n" +
                                "\n" +
                                "Step 2: Bow forward, draping your torso between your thighs. keep your forehead to come to the floor.\n" +
                                "\n" +
                                "Step 3: Keep your arms long and extended, palms facing down. Press back slightly with your hands to keep your buttocks in contact with your heels.  \n" +
                                "\n" +
                                "Hold for up to a minute or longer, breathing softly.\n");
                ((ImageView) subItem4.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_child_traditional);

                //5th pose
                ExpandingItem item5 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item5.findViewById(R.id.title)).setText("Standing Forward Bend");
                item5.setIndicatorIconRes(R.drawable.yoga_forward_bend);
                item5.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item5.createSubItems(1);

                View subItem5 = item5.getSubItemView(0);
                ((TextView) subItem5.findViewById(R.id.sub_title))
                        .setText("Step 1: bend your torso such that your torso and chest touches your thighs. bend forward from the hip joints. draw the front torso out of the groins and open the space between the pubis and top sternum. \n" +
                                "\n" +
                                "Step 2: bend your head down with your knees straight, bring your palms or finger tips to the floor slightly in front of or beside your feet, or bring your palms to the backs of your ankles. \n" +
                                "\n" +
                                "Step 3: bend your elbows and keep your legs straight.\n");
                ((ImageView) subItem5.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_forward_bend);


                //6th pose
                ExpandingItem item6 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item6.findViewById(R.id.title)).setText("Tree Pose");
                item6.setIndicatorIconRes(R.drawable.yoga_tree);
                item6.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item6.createSubItems(1);

                View subItem6 = item6.getSubItemView(0);
                ((TextView) subItem6.findViewById(R.id.sub_title))
                        .setText("Step 1: Begin standing with your arms at your sides and Bend your right knee, then reach down and clasp your right inner ankle. draw your right foot alongside your inner left thigh. \n" +
                                "\n" +
                                "Step 2: Adjust your position so the center of your pelvis is directly over your left foot. Then, adjust your hips so your right hip and left hip are aligned.\n" +
                                "\n" +
                                "Step 3: Press your palms together in prayer position at your chest, with your thumbs resting on your sternum.\n" +
                                "\n" +
                                "Step 4: Inhale as you extend your arms overhead, reaching your fingertips to the sky.\n" +
                                "\n" +
                                "Hold for up to one minute.\n");
                ((ImageView) subItem6.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_tree);


                //7th pose
                ExpandingItem item7 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item7.findViewById(R.id.title)).setText("Cat Pose");
                item7.setIndicatorIconRes(R.drawable.yoga_cat);
                item7.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item7.createSubItems(1);

                View subItem7 = item7.getSubItemView(0);
                ((TextView) subItem7.findViewById(R.id.sub_title))
                        .setText("Step 1: Start on your hands and knees with your wrists directly under your shoulders, and your knees directly under your hips. Center your head in a neutral position and soften your gaze downward.\n" +
                                "\n" +
                                "Step 2: Lift your chin and chest, and gaze up toward the ceiling.\n" +
                                "\n" +
                                "Step 3: Repeat 5-20 times, and then rest by sitting back on your heels with your torso upright.\n");
                ((ImageView) subItem7.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_cat);


                //8th pose
                ExpandingItem item8 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item8.findViewById(R.id.title)).setText("High Cobra Pose");
                item8.setIndicatorIconRes(R.drawable.yoga_cobra_full);
                item8.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item8.createSubItems(1);

                View subItem8 = item8.getSubItemView(0);
                ((TextView) subItem8.findViewById(R.id.sub_title))
                        .setText("Step 1: Begin by lying face-down on the floor with your legs extended behind you. The tops of your feet should rest on the mat.\n" +
                                "\n" +
                                "Step 2: Place your hands under your shoulders with your fingers pointing toward the top of the mat.\n" +
                                "\n" +
                                "Step 3: Tilt head back and look up and lift chest up. Keep your lower ribs on the floor. \n" +
                                "\n" +
                                "Step 4: Draw your shoulders back and your heart forward. Press the tops of your thighs down firmly into the floor. \n" +
                                "\n" +
                                "Hold the pose for up to 30 seconds\n");
                ((ImageView) subItem8.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_cobra_full);


                //9th pose
                ExpandingItem item9 = expandingList.createNewItem(R.layout.expanding_layout);
                ((TextView) item9.findViewById(R.id.title)).setText("Thunderbolt Pose");
                item9.setIndicatorIconRes(R.drawable.yoga_thunderbolt);
                item9.setIndicatorColor(Color.parseColor("#E7E7E7"));

                item9.createSubItems(1);

                View subItem9 = item9.getSubItemView(0);
                ((TextView) subItem9.findViewById(R.id.sub_title))
                        .setText("Step 1: Start by kneeling on the floor. \n" +
                                "\n" +
                                "Step 2: Pull your knees and ankles together. buttocks will rest on your heels and your thighs will rest on your calves.\n" +
                                "\n" +
                                "Step 3: Put your hands on your knees. keep the arms and back straight.\n");
                ((ImageView) subItem9.findViewById(R.id.sub_image)).setImageResource(R.drawable.yoga_thunderbolt);
            }
        });

    }


    // To Fade out age Range Buttons and Fade in Activities list

    public void disappear() {
        LinearLayout linearLayout = findViewById(R.id.parent);
        LinearLayout linearLayout1 = findViewById(R.id.choices_layout);

        transition.setDuration(600).addTarget(ask_age).addTarget(linearLayout1).addTarget(expandingList);
        TransitionManager.beginDelayedTransition(linearLayout, transition);
        ask_age.setVisibility(View.GONE);
        linearLayout1.setVisibility(View.GONE);
        expandingList.setVisibility(View.VISIBLE);
    }
}