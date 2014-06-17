# Android NFC Library


What you'll need when using this library :

|Android        | ver |
|:-------------|:------:|
| API| 16|


## Quick start

In order to get a quick demo running you could perform the following steps : 

*  Add this to the repositories block : 
   	
``` groovy
	repositories {
   		maven{
        	url "http://www.appfoundry.be/maven"
    	}
	}
```	
* Go to your project's `build.gradle` file, and change the dependencies block to match the following line of code there : 
   
``` groovy
    compile 'be.appfoundry:nfc-lib:1.0'
```

Now go to the created activity, and either 

* Implement [FGD] yourself

```	java
	public class MyActivity{
	    private PendingIntent pendingIntent;
    	private IntentFilter[] mIntentFilters;
   		private String[][] mTechLists;
    	private NfcAdapter mNfcAdapter;
    	    
  	 	protected void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.activity_main);
        	mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        	pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        	mIntentFilters = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)};
        	mTechLists = new String[][]{new String[]{Ndef.class.getName()},
                    new String[]{NdefFormatable.class.getName()}};
                        	}
		public void onResume(){
			super.onResume();
    		if (mNfcAdapter != null) {
       			mNfcAdapter.enableForegroundDispatch(this, pendingIntent, mIntentFilters, mTechLists);
    		}
    	}
    	public void onPause(){
    		super.onPause();
    		if (mNfcAdapter != null)
        	{
            	mNfcAdapter.disableForegroundDispatch(this);
        	}
        }
	}

```
* Or extend from NfcActivity:

``` java    
	public class MyActivity extends NfcActivity{    	
    	protected void onCreate(Bundle savedInstanceState){
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_main);
    	}
    }
```


## Start Reading

* Paste this in the activity if you're **extending our class** : 


``` java
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
    	for (String message : getNfcMessages()){
       		Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    	}
	}
```

* Otherwise :

``` java
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SparseArray<String> res = new NfcReadUtilityImpl().readFromTagWithSparseArray(intent);
        for (int i =0; i < res.size() ; i++ ) {
            Toast.makeText(this, res.valueAt(i), Toast.LENGTH_SHORT).show();
        }
    }
```
* If you like the Map implementation more you might as well use : 

``` java
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        for (String message : new NfcReadUtilityImpl().readFromTagWithMap(intent).values()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
```

* Now you're able to read the NFC Tags as long as the library supports the data in it when held to your phone!
* See [Reading]

## Write to a tag
* Let your activity implement `AsyncUiCallback`: 


``` java
    @Override
    public void callbackWithReturnValue(Boolean result) {
        String message = result ? "Success" : "Failed!";
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onProgressUpdate(Boolean... booleans) {
        Toast.makeText(this, booleans[0] ? "We started writing" : "We could not write!",Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onError(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }
```
    
* Create a field with an `AsyncOperationCallback` in the following way :
        
``` java
	AsyncOperationCallback mAsyncOperationCallback = new AsyncOperationCallback() {
	
        @Override
        public boolean performWrite(NfcWriteUtility writeUtility) throws ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException, FormatException {
            return writeUtility.writeEmailToTagFromIntent("some@email.tld","Subject","Message",getIntent());
        }
    };
```

* Override the `onNewIntent(Intent)` method in the following way :

``` java
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    new WriteEmailNfcAsync(this,mAsyncOperationCallback).executeWriteOperation();
	}
```
* If you hold a tag against the phone and it is NFC Enabled, your implementation of the methods will be executed.



[Reading]:ReadUtility.md
[NFC Forum]:http://members.nfc-forum.org/specs/
[FGD]:http://developer.android.com/guide/topics/connectivity/nfc/advanced-nfc.html#foreground-dispatch