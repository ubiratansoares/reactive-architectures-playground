package br.ufs.demos.rxmvp.playground;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import br.ufs.demos.rxmvp.playground.app.MainApplication;
import br.ufs.demos.rxmvp.playground.fakes.FakeWebService;

/**
 * Created by bira on 10/3/17.
 */

public class ScriptableWebServerTestRule<T extends Activity> extends ActivityTestRule<T> {

    private FakeWebService mockWebService;
    private FakeWebService.NextCall next;

    public ScriptableWebServerTestRule(Class<T> activityClass) {

        super(activityClass, false, false);
        setupWebService();
    }

    @Override protected void beforeActivityLaunched() {
        mockWebService.nextCallPerform(next);
        super.beforeActivityLaunched();
    }

    public void startWithCenario(FakeWebService.NextCall next) {
        this.next = next;
        launchActivity(new Intent());
    }

    private void setupWebService() {
        Application main =
                (Application) InstrumentationRegistry.getInstrumentation()
                        .getTargetContext()
                        .getApplicationContext();

        MainApplication app = (MainApplication) main;
        mockWebService = (FakeWebService) app.webservice;
    }

}
