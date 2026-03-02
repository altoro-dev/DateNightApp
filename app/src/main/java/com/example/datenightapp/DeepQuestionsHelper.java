package com.example.datenightapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database for Let's Get Deep questions
 * Contains 300 questions across 3 levels:
 * - Ice Breaker: 100 questions
 * - Deep: 100 questions
 * - Deeper: 100 questions
 */
public class DeepQuestionsHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DeepQuestions.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_QUESTIONS = "questions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_IS_DISCUSSED = "isDiscussed";
    private static final String COLUMN_IS_FAVORITE = "isFavorite";
    private static final String COLUMN_ORDER_INDEX = "orderIndex";

    public DeepQuestionsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LEVEL + " TEXT NOT NULL,"
                + COLUMN_QUESTION + " TEXT NOT NULL,"
                + COLUMN_IS_DISCUSSED + " INTEGER DEFAULT 0,"
                + COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0,"
                + COLUMN_ORDER_INDEX + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);

        // Add all 300 questions
        addAllQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    // Add all 300 questions to database
    private void addAllQuestions(SQLiteDatabase db) {
        addIceBreakerQuestions(db);
        addDeepQuestions(db);
        addDeeperQuestions(db);
    }

    // Helper method to insert a question
    private void insertQuestion(SQLiteDatabase db, String level, String question, int orderIndex) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LEVEL, level);
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_ORDER_INDEX, orderIndex);
        values.put(COLUMN_IS_DISCUSSED, 0);
        values.put(COLUMN_IS_FAVORITE, 0);
        db.insert(TABLE_QUESTIONS, null, values);
    }

    // 100 Ice Breaker questions
    private void addIceBreakerQuestions(SQLiteDatabase db) {
        String level = "icebreaker";
        int index = 1;

        // Fun & Playful (1-30)
        insertQuestion(db, level, "What's your ideal date night?", index++);
        insertQuestion(db, level, "If you could travel anywhere together, where would we go?", index++);
        insertQuestion(db, level, "What's your guilty pleasure TV show or movie?", index++);
        insertQuestion(db, level, "What's the best gift you've ever received?", index++);
        insertQuestion(db, level, "If you could have any superpower, what would it be?", index++);
        insertQuestion(db, level, "What's your favorite way to spend a lazy Sunday?", index++);
        insertQuestion(db, level, "What song always makes you smile?", index++);
        insertQuestion(db, level, "What's your favorite memory from childhood?", index++);
        insertQuestion(db, level, "If you could have dinner with anyone, who would it be?", index++);
        insertQuestion(db, level, "What's something that always makes you laugh?", index++);
        insertQuestion(db, level, "What's your comfort food?", index++);
        insertQuestion(db, level, "What's the best advice you've ever received?", index++);
        insertQuestion(db, level, "If you won the lottery, what's the first thing you'd buy?", index++);
        insertQuestion(db, level, "What's your favorite season and why?", index++);
        insertQuestion(db, level, "What hobby have you always wanted to try?", index++);
        insertQuestion(db, level, "What's your go-to karaoke song?", index++);
        insertQuestion(db, level, "What's the most adventurous thing you've ever done?", index++);
        insertQuestion(db, level, "What's your favorite way to relax after a long day?", index++);
        insertQuestion(db, level, "If you could live in any time period, when would it be?", index++);
        insertQuestion(db, level, "What's something you're really good at?", index++);
        insertQuestion(db, level, "What's your favorite holiday and why?", index++);
        insertQuestion(db, level, "If you could be famous for something, what would it be?", index++);
        insertQuestion(db, level, "What's the most spontaneous thing you've ever done?", index++);
        insertQuestion(db, level, "What's your favorite thing about where we live?", index++);
        insertQuestion(db, level, "What's a skill you'd love to master?", index++);
        insertQuestion(db, level, "What's your favorite memory of us together?", index++);
        insertQuestion(db, level, "If you could switch lives with someone for a day, who would it be?", index++);
        insertQuestion(db, level, "What's the best concert or show you've ever been to?", index++);
        insertQuestion(db, level, "What's your favorite way to celebrate special occasions?", index++);
        insertQuestion(db, level, "What's something that always puts you in a good mood?", index++);

        // Preferences & Favorites (31-50)
        insertQuestion(db, level, "What's your love language?", index++);
        insertQuestion(db, level, "Morning person or night owl?", index++);
        insertQuestion(db, level, "Coffee or tea?", index++);
        insertQuestion(db, level, "Beach vacation or mountain getaway?", index++);
        insertQuestion(db, level, "What's your favorite type of cuisine?", index++);
        insertQuestion(db, level, "Do you prefer planning ahead or being spontaneous?", index++);
        insertQuestion(db, level, "What's your favorite book or genre?", index++);
        insertQuestion(db, level, "Introvert or extrovert?", index++);
        insertQuestion(db, level, "What's your favorite way to exercise or stay active?", index++);
        insertQuestion(db, level, "Do you prefer texting or calling?", index++);
        insertQuestion(db, level, "What's your favorite type of music?", index++);
        insertQuestion(db, level, "Would you rather host a party or attend one?", index++);
        insertQuestion(db, level, "What's your favorite thing to cook?", index++);
        insertQuestion(db, level, "Do you prefer dogs or cats?", index++);
        insertQuestion(db, level, "What's your favorite way to spend a rainy day?", index++);
        insertQuestion(db, level, "Sweet or savory snacks?", index++);
        insertQuestion(db, level, "What's your favorite childhood game?", index++);
        insertQuestion(db, level, "Do you prefer surprises or knowing what's coming?", index++);
        insertQuestion(db, level, "What's your ideal temperature?", index++);
        insertQuestion(db, level, "What's your favorite outdoor activity?", index++);

        // Getting to Know You (51-80)
        insertQuestion(db, level, "What did you want to be when you grew up?", index++);
        insertQuestion(db, level, "What's something most people don't know about you?", index++);
        insertQuestion(db, level, "What's a talent you have that might surprise me?", index++);
        insertQuestion(db, level, "What was your favorite subject in school?", index++);
        insertQuestion(db, level, "What's the best piece of advice your parents gave you?", index++);
        insertQuestion(db, level, "What's your earliest memory?", index++);
        insertQuestion(db, level, "What's something you collect or used to collect?", index++);
        insertQuestion(db, level, "What's the most interesting place you've ever visited?", index++);
        insertQuestion(db, level, "What's your favorite family tradition?", index++);
        insertQuestion(db, level, "What's something you're proud of accomplishing?", index++);
        insertQuestion(db, level, "What nickname did you have growing up?", index++);
        insertQuestion(db, level, "What's your favorite thing about your best friend?", index++);
        insertQuestion(db, level, "What was your first job?", index++);
        insertQuestion(db, level, "What's the most embarrassing thing that's happened to you?", index++);
        insertQuestion(db, level, "What's your favorite smell?", index++);
        insertQuestion(db, level, "What's the best meal you've ever had?", index++);
        insertQuestion(db, level, "What's something you wish you were better at?", index++);
        insertQuestion(db, level, "What's your favorite local spot in our city?", index++);
        insertQuestion(db, level, "What's the weirdest food you've ever tried?", index++);
        insertQuestion(db, level, "What's your favorite childhood toy or game?", index++);
        insertQuestion(db, level, "What's the best birthday you've ever had?", index++);
        insertQuestion(db, level, "What's your go-to dance move?", index++);
        insertQuestion(db, level, "What's something you do to de-stress?", index++);
        insertQuestion(db, level, "What's your favorite outfit or style?", index++);
        insertQuestion(db, level, "What's a movie or show you can watch over and over?", index++);
        insertQuestion(db, level, "What's the best compliment you've ever received?", index++);
        insertQuestion(db, level, "What's your favorite thing about your hometown?", index++);
        insertQuestion(db, level, "What's something that makes you feel nostalgic?", index++);
        insertQuestion(db, level, "What's your favorite way to treat yourself?", index++);
        insertQuestion(db, level, "What's the last thing that made you laugh really hard?", index++);

        // Future & Dreams (81-100)
        insertQuestion(db, level, "Where do you see us in 5 years?", index++);
        insertQuestion(db, level, "What's one place you want to visit together?", index++);
        insertQuestion(db, level, "What's a goal you want to achieve this year?", index++);
        insertQuestion(db, level, "What's something on your bucket list?", index++);
        insertQuestion(db, level, "What's your dream job?", index++);
        insertQuestion(db, level, "If money wasn't an issue, what would you do with your time?", index++);
        insertQuestion(db, level, "What kind of legacy do you want to leave?", index++);
        insertQuestion(db, level, "What's something you want to learn together?", index++);
        insertQuestion(db, level, "What does your perfect day look like?", index++);
        insertQuestion(db, level, "What adventure do you want us to go on?", index++);
        insertQuestion(db, level, "What's a tradition you want to start with us?", index++);
        insertQuestion(db, level, "What's something you want to accomplish together?", index++);
        insertQuestion(db, level, "If we could move anywhere, where would you want to go?", index++);
        insertQuestion(db, level, "What's a skill you want us to learn together?", index++);
        insertQuestion(db, level, "What's your dream vacation?", index++);
        insertQuestion(db, level, "What's one thing you want to change in your life?", index++);
        insertQuestion(db, level, "What does retirement look like for you?", index++);
        insertQuestion(db, level, "What's something you want to try but haven't yet?", index++);
        insertQuestion(db, level, "What kind of home do you dream of living in?", index++);
        insertQuestion(db, level, "What's your vision for our future together?", index++);
    }

    // 100 Deep questions
    private void addDeepQuestions(SQLiteDatabase db) {
        String level = "deep";
        int index = 1;

        // Relationships & Love (1-25)
        insertQuestion(db, level, "What does love mean to you?", index++);
        insertQuestion(db, level, "What's your biggest fear about our relationship?", index++);
        insertQuestion(db, level, "How do you define a successful relationship?", index++);
        insertQuestion(db, level, "What makes you feel most loved?", index++);
        insertQuestion(db, level, "What does commitment mean to you?", index++);
        insertQuestion(db, level, "How do you handle conflict in relationships?", index++);
        insertQuestion(db, level, "What's something I do that makes you feel appreciated?", index++);
        insertQuestion(db, level, "What's your biggest relationship strength?", index++);
        insertQuestion(db, level, "What's something you need more of in our relationship?", index++);
        insertQuestion(db, level, "How do you want to grow together as a couple?", index++);
        insertQuestion(db, level, "What does intimacy mean to you beyond physical?", index++);
        insertQuestion(db, level, "What's your idea of perfect communication?", index++);
        insertQuestion(db, level, "What relationship pattern from your past do you want to break?", index++);
        insertQuestion(db, level, "How do you want me to support you when you're struggling?", index++);
        insertQuestion(db, level, "What does trust mean to you?", index++);
        insertQuestion(db, level, "What's something you learned from a past relationship?", index++);
        insertQuestion(db, level, "How do you define emotional safety?", index++);
        insertQuestion(db, level, "What's your biggest relationship insecurity?", index++);
        insertQuestion(db, level, "How can we better support each other's dreams?", index++);
        insertQuestion(db, level, "What does partnership mean to you?", index++);
        insertQuestion(db, level, "What's one thing you wish we did more as a couple?", index++);
        insertQuestion(db, level, "How do you want to handle disagreements?", index++);
        insertQuestion(db, level, "What role does physical affection play in feeling connected?", index++);
        insertQuestion(db, level, "What's something you appreciate about how we communicate?", index++);
        insertQuestion(db, level, "What does 'being there' for someone mean to you?", index++);

        // Values & Beliefs (26-50)
        insertQuestion(db, level, "What are your top 3 core values?", index++);
        insertQuestion(db, level, "What does family mean to you?", index++);
        insertQuestion(db, level, "How important is career success to you?", index++);
        insertQuestion(db, level, "What role does spirituality or faith play in your life?", index++);
        insertQuestion(db, level, "What do you believe is the purpose of life?", index++);
        insertQuestion(db, level, "How do you define personal success?", index++);
        insertQuestion(db, level, "What's something you're passionate about?", index++);
        insertQuestion(db, level, "What does work-life balance mean to you?", index++);
        insertQuestion(db, level, "How important is financial security to you?", index++);
        insertQuestion(db, level, "What causes or issues do you care most about?", index++);
        insertQuestion(db, level, "What does being a good person mean to you?", index++);
        insertQuestion(db, level, "How do you want to make a difference in the world?", index++);
        insertQuestion(db, level, "What traditions are important to you?", index++);
        insertQuestion(db, level, "How do you define happiness?", index++);
        insertQuestion(db, level, "What does personal growth mean to you?", index++);
        insertQuestion(db, level, "What's your philosophy on forgiveness?", index++);
        insertQuestion(db, level, "How important is adventure vs. stability to you?", index++);
        insertQuestion(db, level, "What does authenticity mean to you?", index++);
        insertQuestion(db, level, "How do you want to raise children? (or approach family)", index++);
        insertQuestion(db, level, "What's your view on maintaining friendships while in a relationship?", index++);
        insertQuestion(db, level, "What does it mean to live with purpose?", index++);
        insertQuestion(db, level, "How do you define emotional maturity?", index++);
        insertQuestion(db, level, "What's your stance on vulnerability?", index++);
        insertQuestion(db, level, "What role does gratitude play in your life?", index++);
        insertQuestion(db, level, "How do you approach making big life decisions?", index++);

        // Growth & Self-Awareness (51-75)
        insertQuestion(db, level, "What's something you're working on improving about yourself?", index++);
        insertQuestion(db, level, "What's your biggest personal challenge right now?", index++);
        insertQuestion(db, level, "What's a pattern in your life you want to change?", index++);
        insertQuestion(db, level, "What does self-care mean to you?", index++);
        insertQuestion(db, level, "What's something you're learning about yourself?", index++);
        insertQuestion(db, level, "What triggers your stress or anxiety?", index++);
        insertQuestion(db, level, "How do you practice self-compassion?", index++);
        insertQuestion(db, level, "What's a limiting belief you're trying to overcome?", index++);
        insertQuestion(db, level, "What makes you feel most like yourself?", index++);
        insertQuestion(db, level, "How do you recharge emotionally?", index++);
        insertQuestion(db, level, "What's something you've had to unlearn?", index++);
        insertQuestion(db, level, "What boundaries are important to you?", index++);
        insertQuestion(db, level, "What does personal accountability mean to you?", index++);
        insertQuestion(db, level, "How do you handle failure or disappointment?", index++);
        insertQuestion(db, level, "What's your relationship with change?", index++);
        insertQuestion(db, level, "What childhood message still affects you?", index++);
        insertQuestion(db, level, "How do you deal with uncomfortable emotions?", index++);
        insertQuestion(db, level, "What does it mean to be true to yourself?", index++);
        insertQuestion(db, level, "What aspects of yourself are you still discovering?", index++);
        insertQuestion(db, level, "How do you show up for yourself?", index++);
        insertQuestion(db, level, "What does emotional intelligence mean to you?", index++);
        insertQuestion(db, level, "What's a strength you didn't always recognize in yourself?", index++);
        insertQuestion(db, level, "How do you practice mindfulness or presence?", index++);
        insertQuestion(db, level, "What does healing mean to you?", index++);
        insertQuestion(db, level, "What part of your journey are you most proud of?", index++);

        // Fears & Vulnerabilities (76-100)
        insertQuestion(db, level, "What's your biggest fear in life?", index++);
        insertQuestion(db, level, "What makes you feel most vulnerable?", index++);
        insertQuestion(db, level, "What's something you worry about often?", index++);
        insertQuestion(db, level, "What criticism hurts you the most?", index++);
        insertQuestion(db, level, "What's your biggest regret so far?", index++);
        insertQuestion(db, level, "What do you fear about getting older?", index++);
        insertQuestion(db, level, "What's something that keeps you up at night?", index++);
        insertQuestion(db, level, "What makes you feel inadequate?", index++);
        insertQuestion(db, level, "What's a difficult truth you've had to accept?", index++);
        insertQuestion(db, level, "What do you fear losing most?", index++);
        insertQuestion(db, level, "What's something you're afraid to hope for?", index++);
        insertQuestion(db, level, "What makes you feel lonely?", index++);
        insertQuestion(db, level, "What's your biggest source of stress?", index++);
        insertQuestion(db, level, "What do you fear about failure?", index++);
        insertQuestion(db, level, "What's something you're afraid to admit?", index++);
        insertQuestion(db, level, "What situation makes you feel powerless?", index++);
        insertQuestion(db, level, "What's your greatest source of anxiety?", index++);
        insertQuestion(db, level, "What do you fear about intimacy?", index++);
        insertQuestion(db, level, "What makes you doubt yourself?", index++);
        insertQuestion(db, level, "What's a fear you've overcome?", index++);
        insertQuestion(db, level, "What do you need reassurance about?", index++);
        insertQuestion(db, level, "What's something you're afraid will never change?", index++);
        insertQuestion(db, level, "What makes you feel most uncertain?", index++);
        insertQuestion(db, level, "What's a mistake you're afraid of repeating?", index++);
        insertQuestion(db, level, "What do you fear people will discover about you?", index++);
    }

    // Add 100 Deeper questions
    private void addDeeperQuestions(SQLiteDatabase db) {
        String level = "deeper";
        int index = 1;

        // Deep Vulnerability (1-25)
        insertQuestion(db, level, "What's something you've never told anyone?", index++);
        insertQuestion(db, level, "What part of yourself do you struggle to accept?", index++);
        insertQuestion(db, level, "What do you need most from me right now?", index++);
        insertQuestion(db, level, "What wound from your past still hurts today?", index++);
        insertQuestion(db, level, "When do you feel most misunderstood?", index++);
        insertQuestion(db, level, "What's the hardest thing you've ever had to forgive?", index++);
        insertQuestion(db, level, "What truth about yourself are you avoiding?", index++);
        insertQuestion(db, level, "What do you wish you could change about your childhood?", index++);
        insertQuestion(db, level, "What's your deepest insecurity?", index++);
        insertQuestion(db, level, "What makes you feel unworthy?", index++);
        insertQuestion(db, level, "What's something you're ashamed of?", index++);
        insertQuestion(db, level, "What part of your past are you still healing from?", index++);
        insertQuestion(db, level, "What do you need to hear most right now?", index++);
        insertQuestion(db, level, "What's the most painful thing you've experienced?", index++);
        insertQuestion(db, level, "What makes you feel truly seen?", index++);
        insertQuestion(db, level, "What burden are you carrying that you want to let go of?", index++);
        insertQuestion(db, level, "What version of yourself are you grieving?", index++);
        insertQuestion(db, level, "What do you wish your younger self knew?", index++);
        insertQuestion(db, level, "What betrayal hurt you the most?", index++);
        insertQuestion(db, level, "What's something you wish you could redo?", index++);
        insertQuestion(db, level, "What makes you feel alone, even when you're not?", index++);
        insertQuestion(db, level, "What part of love scares you the most?", index++);
        insertQuestion(db, level, "What's the bravest thing you've ever done emotionally?", index++);
        insertQuestion(db, level, "What do you wish I understood better about you?", index++);
        insertQuestion(db, level, "What's holding you back from being your fullest self?", index++);

        // Soul-Level Connection (26-50)
        insertQuestion(db, level, "What does your soul need to thrive?", index++);
        insertQuestion(db, level, "What moment changed who you are forever?", index++);
        insertQuestion(db, level, "What does unconditional love mean to you?", index++);
        insertQuestion(db, level, "What's your deepest desire for our relationship?", index++);
        insertQuestion(db, level, "What do you believe we're meant to teach each other?", index++);
        insertQuestion(db, level, "What does it mean to truly know someone?", index++);
        insertQuestion(db, level, "What's the most intimate moment we've shared?", index++);
        insertQuestion(db, level, "What makes you feel most connected to me?", index++);
        insertQuestion(db, level, "What do you hope I never stop doing for you?", index++);
        insertQuestion(db, level, "What's something about us that feels sacred?", index++);
        insertQuestion(db, level, "How has loving me changed you?", index++);
        insertQuestion(db, level, "What's the deepest way you've hurt someone you loved?", index++);
        insertQuestion(db, level, "What does 'home' feel like to you?", index++);
        insertQuestion(db, level, "What part of our relationship feels most fragile?", index++);
        insertQuestion(db, level, "What do you believe our souls recognized in each other?", index++);
        insertQuestion(db, level, "What does being fully yourself with me feel like?", index++);
        insertQuestion(db, level, "What's a truth about love you've learned the hard way?", index++);
        insertQuestion(db, level, "How do you want to be remembered by me?", index++);
        insertQuestion(db, level, "What's the most courageous thing you've done in our relationship?", index++);
        insertQuestion(db, level, "What does forever mean to you?", index++);
        insertQuestion(db, level, "What aspect of intimacy is hardest for you?", index++);
        insertQuestion(db, level, "What does it mean to truly let someone in?", index++);
        insertQuestion(db, level, "What's your deepest hope for us?", index++);
        insertQuestion(db, level, "What wall between us do you wish we could break down?", index++);
        insertQuestion(db, level, "What does choosing each other every day mean to you?", index++);

        // Life's Big Questions (51-75)
        insertQuestion(db, level, "What do you believe happens after we die?", index++);
        insertQuestion(db, level, "What gives your life meaning?", index++);
        insertQuestion(db, level, "What do you believe you're here to do?", index++);
        insertQuestion(db, level, "What legacy do you want to leave?", index++);
        insertQuestion(db, level, "What does it mean to live a good life?", index++);
        insertQuestion(db, level, "What would you do if you had one year left to live?", index++);
        insertQuestion(db, level, "What do you believe about fate vs. free will?", index++);
        insertQuestion(db, level, "What's your philosophy on suffering?", index++);
        insertQuestion(db, level, "What do you think is humanity's greatest challenge?", index++);
        insertQuestion(db, level, "What's your relationship with death?", index++);
        insertQuestion(db, level, "What do you believe about the nature of consciousness?", index++);
        insertQuestion(db, level, "What's something you believe that most people don't?", index++);
        insertQuestion(db, level, "What does it mean to be fully alive?", index++);
        insertQuestion(db, level, "What's your understanding of suffering and growth?", index++);
        insertQuestion(db, level, "What do you believe about the interconnectedness of all things?", index++);
        insertQuestion(db, level, "What's your greatest existential fear?", index++);
        insertQuestion(db, level, "What does wisdom mean to you?", index++);
        insertQuestion(db, level, "What do you believe about the concept of soulmates?", index++);
        insertQuestion(db, level, "What's your view on human nature - good, bad, or neutral?", index++);
        insertQuestion(db, level, "What role does suffering play in becoming who you're meant to be?", index++);
        insertQuestion(db, level, "What do you believe is the relationship between pain and love?", index++);
        insertQuestion(db, level, "What does transcendence mean to you?", index++);
        insertQuestion(db, level, "What's your biggest question about existence?", index++);
        insertQuestion(db, level, "What do you believe is worth dying for?", index++);
        insertQuestion(db, level, "What does it mean to be human?", index++);

        // Deepest Desires & Dreams (76-100)
        insertQuestion(db, level, "What's a dream you've given up on that still calls to you?", index++);
        insertQuestion(db, level, "What's something you desperately want but are afraid to pursue?", index++);
        insertQuestion(db, level, "What version of yourself are you most afraid to become?", index++);
        insertQuestion(db, level, "What do you wish you had the courage to do?", index++);
        insertQuestion(db, level, "What's your deepest fantasy for our life together?", index++);
        insertQuestion(db, level, "What part of yourself have you suppressed?", index++);
        insertQuestion(db, level, "What would you do if you knew you couldn't fail?", index++);
        insertQuestion(db, level, "What's the most authentic version of yourself?", index++);
        insertQuestion(db, level, "What freedom are you most craving?", index++);
        insertQuestion(db, level, "What's a desire you're afraid to voice?", index++);
        insertQuestion(db, level, "What would your life look like if you weren't afraid?", index++);
        insertQuestion(db, level, "What's something you want to experience before you die?", index++);
        insertQuestion(db, level, "What part of your potential feels untapped?", index++);
        insertQuestion(db, level, "What's your wildest dream for yourself?", index++);
        insertQuestion(db, level, "What transformation are you most hungry for?", index++);
        insertQuestion(db, level, "What would healing look like for you?", index++);
        insertQuestion(db, level, "What's a version of us you can imagine but haven't voiced?", index++);
        insertQuestion(db, level, "What's something you wish you could give yourself?", index++);
        insertQuestion(db, level, "What does your heart most want?", index++);
        insertQuestion(db, level, "What would complete freedom feel like?", index++);
        insertQuestion(db, level, "What's the deepest form of intimacy you crave?", index++);
        insertQuestion(db, level, "What would make you feel most alive?", index++);
        insertQuestion(db, level, "What's something you wish the world knew about you?", index++);
        insertQuestion(db, level, "What does your soul hunger for?", index++);
        insertQuestion(db, level, "If you could design our perfect future together, what would it look like?", index++);
    }

    // ========== DATABASE OPERATIONS ==========

    // Get questions by level
    public List<DeepQuestions> getQuestionsByLevel(String level) {
        List<DeepQuestions> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS,
                null,
                COLUMN_LEVEL + "=?",
                new String[]{level},
                null, null,
                COLUMN_ORDER_INDEX + " ASC");

        if (cursor.moveToFirst()) {
            do {
                DeepQuestions question = new DeepQuestions(
                        cursor.getInt(0),     // id
                        cursor.getString(1),  // level
                        cursor.getString(2),  // question
                        cursor.getInt(3) == 1, // isDiscussed
                        cursor.getInt(4) == 1, // isFavorite
                        cursor.getInt(5)      // orderIndex
                );
                questions.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return questions;
    }

    // Get next undiscussed question for a level
    public DeepQuestions getNextQuestion(String level) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS,
                null,
                COLUMN_LEVEL + "=? AND " + COLUMN_IS_DISCUSSED + "=0",
                new String[]{level},
                null, null,
                COLUMN_ORDER_INDEX + " ASC",
                "1");

        DeepQuestions question = null;
        if (cursor.moveToFirst()) {
            question = new DeepQuestions(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3) == 1, cursor.getInt(4) == 1, cursor.getInt(5)
            );
        }

        cursor.close();
        db.close();
        return question;
    }

    // Get current question
    public DeepQuestions getCurrentQuestion(String level) {
        SQLiteDatabase db = this.getReadableDatabase();

        // First try to get last discussed question
        Cursor cursor = db.query(TABLE_QUESTIONS,
                null,
                COLUMN_LEVEL + "=? AND " + COLUMN_IS_DISCUSSED + "=1",
                new String[]{level},
                null, null,
                COLUMN_ORDER_INDEX + " DESC",
                "1");

        DeepQuestions question = null;
        if (cursor.moveToFirst()) {
            // Found last discussed, return it
            question = new DeepQuestions(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3) == 1, cursor.getInt(4) == 1, cursor.getInt(5)
            );
        } else {
            // No discussed questions yet, get first question
            cursor.close();
            cursor = db.query(TABLE_QUESTIONS,
                    null,
                    COLUMN_LEVEL + "=?",
                    new String[]{level},
                    null, null,
                    COLUMN_ORDER_INDEX + " ASC",
                    "1");

            if (cursor.moveToFirst()) {
                question = new DeepQuestions(
                        cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3) == 1, cursor.getInt(4) == 1, cursor.getInt(5)
                );
            }
        }

        cursor.close();
        db.close();
        return question;
    }

    // Mark question as discussed
    public void markAsDiscussed(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_DISCUSSED, 1);
        db.update(TABLE_QUESTIONS, values, COLUMN_ID + "=?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    // Toggle favorite status
    public void toggleFavorite(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get current status
        Cursor cursor = db.query(TABLE_QUESTIONS,
                new String[]{COLUMN_IS_FAVORITE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(questionId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            boolean currentStatus = cursor.getInt(0) == 1;
            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_FAVORITE, currentStatus ? 0 : 1);
            db.update(TABLE_QUESTIONS, values, COLUMN_ID + "=?", new String[]{String.valueOf(questionId)});
        }

        cursor.close();
        db.close();
    }

    // Get all favorite questions
    public List<DeepQuestions> getFavoriteQuestions() {
        List<DeepQuestions> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS,
                null,
                COLUMN_IS_FAVORITE + "=1",
                null,
                null, null,
                COLUMN_LEVEL + " ASC, " + COLUMN_ORDER_INDEX + " ASC");

        if (cursor.moveToFirst()) {
            do {
                DeepQuestions question = new DeepQuestions(
                        cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3) == 1, cursor.getInt(4) == 1, cursor.getInt(5)
                );
                questions.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return questions;
    }

    // Get progress for a level (count of discussed questions)
    public int getProgress(String level) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_QUESTIONS +
                        " WHERE " + COLUMN_LEVEL + "=? AND " + COLUMN_IS_DISCUSSED + "=1",
                new String[]{level});

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    // Reset progress for a level
    public void resetProgress(String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_DISCUSSED, 0);
        db.update(TABLE_QUESTIONS, values, COLUMN_LEVEL + "=?", new String[]{level});
        db.close();
    }

    // Reset all progress
    public void resetAllProgress() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_DISCUSSED, 0);
        db.update(TABLE_QUESTIONS, values, null, null);
        db.close();
    }

    // Get a specific question by ID
    public DeepQuestions getQuestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        DeepQuestions question = null;
        if (cursor.moveToFirst()) {
            question = new DeepQuestions(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3) == 1, cursor.getInt(4) == 1, cursor.getInt(5)
            );
        }

        cursor.close();
        db.close();
        return question;
    }
}