package com.ihfazh.jadwal_ku.screens.common.fragmentframehelper;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ihfazh.jadwal_ku.screens.eventdetail.EventDetailFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import kotlin.jvm.functions.Function1;

public class FragmentFrameHelper {
    private final AppCompatActivity mActivity;
    private final FragmentFrameWrapper mFragmentFrameWrapper;
    private final FragmentManager mFragmentManager;

    @Inject
    public FragmentFrameHelper(AppCompatActivity activity, FragmentFrameWrapper fragmentFrameWrapper, FragmentManager fragmentManager) {
        mActivity = activity;
        mFragmentFrameWrapper = fragmentFrameWrapper;
        mFragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment newFragment) {
        if (getCurrentFragment().getClass().equals(newFragment.getClass())) {
            return;
        }
        replaceFragment(newFragment, true, false);

    }

    public void replaceFragment(Fragment newFragment, FragmentTransactionHelper ftHelper) {
        if (getCurrentFragment().getClass().equals(newFragment.getClass())) {
            return;
        }
        replaceFragment(newFragment, true, false, ftHelper);

    }

    public void replaceFragmentDontAddToBackstack(Fragment newFragment) {
        replaceFragment(newFragment, false, false);
    }

    public void replaceFragmentAndClearBackstack(Fragment newFragment) {
        replaceFragment(newFragment, false, true);
    }

    public void navigateUp() {

        // Some navigateUp calls can be "lost" if they happen after the state has been saved
        if (mFragmentManager.isStateSaved()) {
            return;
        }

        Fragment currentFragment = getCurrentFragment();

        if (mFragmentManager.getBackStackEntryCount() > 0) {

            // In a normal world, just popping back stack would be sufficient, but since android
            // is not normal, a call to popBackStack can leave the popped fragment on screen.
            // Therefore, we start with manual removal of the current fragment.
            // Description of the issue can be found here: https://stackoverflow.com/q/45278497/2463035
            removeCurrentFragment();

            if (mFragmentManager.popBackStackImmediate()) {
                return; // navigated "up" in fragments back-stack
            }
        }

        if (HierarchicalFragment.class.isInstance(currentFragment)) {
            Fragment parentFragment =
                    ((HierarchicalFragment)currentFragment).getHierarchicalParentFragment();
            if (parentFragment != null) {
                replaceFragment(parentFragment, false, true);
                return; // navigate "up" to hierarchical parent fragment
            }
        }

        if (mActivity.onNavigateUp()) {
            return; // navigated "up" to hierarchical parent activity
        }

        mActivity.onBackPressed(); // no "up" navigation targets - just treat UP as back press
    }

    private Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(getFragmentFrameId());
    }

    private void replaceFragment(Fragment newFragment, boolean addToBackStack, boolean clearBackStack) {
        if (clearBackStack) {
            if (mFragmentManager.isStateSaved()) {
                // If the state is saved we can't clear the back stack. Simply not doing this, but
                // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                return;
            }
            // Remove all entries from back stack
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        // Change to a new fragment
        ft.replace(getFragmentFrameId(), newFragment, null);

        if (mFragmentManager.isStateSaved()) {
            // We acknowledge the possibility of losing this transaction if the app undergoes
            // save&restore flow after it is committed.
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    private void replaceFragment(Fragment newFragment, boolean addToBackStack, boolean clearBackStack, FragmentTransactionHelper ftHelper) {
        if (clearBackStack) {
            if (mFragmentManager.isStateSaved()) {
                // If the state is saved we can't clear the back stack. Simply not doing this, but
                // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                return;
            }
            // Remove all entries from back stack
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft = ftHelper.updateTransaction(ft);

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        // Change to a new fragment
        ft.replace(getFragmentFrameId(), newFragment, null);

        // manipulate transaction

        if (mFragmentManager.isStateSaved()) {
            // We acknowledge the possibility of losing this transaction if the app undergoes
            // save&restore flow after it is committed.
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }

    private void removeCurrentFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(getCurrentFragment());
        ft.commit();

        // not sure it is needed; will keep it as a reminder to myself if there will be problems
        // mFragmentManager.executePendingTransactions();
    }

    private int getFragmentFrameId() {
        return mFragmentFrameWrapper.getFragmentFrame().getId();
    }


    public void replaceFragment(@NotNull Fragment newFragment, @NotNull Function1<? super FragmentTransaction, ? extends FragmentTransaction> ftCombiner) {
        if (getCurrentFragment().getClass().equals(newFragment.getClass())) {
            return;
        }

        boolean clearBackStack = false;
        boolean addToBackStack = true;

        if (clearBackStack) {
            if (mFragmentManager.isStateSaved()) {
                // If the state is saved we can't clear the back stack. Simply not doing this, but
                // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                return;
            }
            // Remove all entries from back stack
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        // Change to a new fragment
        ft.replace(getFragmentFrameId(), newFragment, null);
        ft = ftCombiner.invoke(ft);

        // manipulate transaction

        if (mFragmentManager.isStateSaved()) {
            // We acknowledge the possibility of losing this transaction if the app undergoes
            // save&restore flow after it is committed.
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
    }
}
