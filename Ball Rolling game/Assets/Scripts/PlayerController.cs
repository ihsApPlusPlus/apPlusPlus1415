using UnityEngine;
using System.Collections;

public class PlayerController : MonoBehaviour {
	public float speed;
	private float gameSpeed;
	private int count;
	private int scoreFactor;
	public int numBlocks;
	private bool canMove;
	private bool canPlay;
	public GUIText instrucText;
	public GUIText instrucText2;
	public GUIText resetText;
	public GUIText countText;
	public GUIText winText;
	public GUIText scoreText;
	public GameObject self;

	void Start() {
		count = 0;
		SetCountText ();
		winText.text = "";
		canMove = true;
		canPlay = false;
		scoreText.text = "";
		resetText.text = "";
		instrucText.text = "";
		instrucText2.text = "";
	}

	void Update  () {
		if (Input.GetKeyDown (KeyCode.Return)) {
			Application.LoadLevel (0);
		}
		if (Input.GetKeyDown (KeyCode.E)) {
			canPlay = true;
			gameSpeed = speed/2;
			scoreFactor = 1;
		}
		else if (Input.GetKeyDown (KeyCode.N)) {
			canPlay = true;
			gameSpeed = speed;
			scoreFactor = 2;
		}
		else if (Input.GetKeyDown (KeyCode.H)) {
			canPlay = true;
			gameSpeed = 2 * speed;
			scoreFactor = 3;
		}
		else if (Input.GetKeyDown (KeyCode.V)) {
			canPlay = true;
			gameSpeed = 5 * speed;
			scoreFactor = 4;
		}
		else if (Input.GetKeyDown (KeyCode.I)) {
			canPlay = true;
			gameSpeed = 10 * speed;
			scoreFactor = 5;
		}
		else if (Input.GetKeyDown (KeyCode.G)) {
			canPlay = true;
			gameSpeed = 300 * speed;
			scoreFactor = 10;
		}
	}

	void FixedUpdate() {
		instructionsText ();
		if (transform.position.y < -10.0) {
			YouLose();
		}
		if ((count < numBlocks) && canMove && canPlay) {
			float moveHorizontal = Input.GetAxis ("Horizontal");
			float moveVertical = Input.GetAxis ("Vertical");
			Vector3 movement = new Vector3 (moveHorizontal, 0, moveVertical);
			rigidbody.AddForce (movement * gameSpeed * Time.deltaTime);
		}
	}

	void OnTriggerEnter (Collider other) {
		if (other.gameObject.tag == "PickUp") {
			count++;
			SetCountText();
			other.gameObject.SetActive (false);
		}
		else if (other.gameObject.tag == "EvilWall") {
			canMove = false;
			YouLose();
		}
	}

	void instructionsText() {
		if (!canPlay) {
			instrucText.text = "Use the arrow keys or WASD to move. Collect the colorful blocks";
			instrucText2.text = "Don't touch the red walls. Press 'e' for easy mode, \n 'n' for normal mode, " +
				"'h' for hard mode, 'v' for very hard mode, \n 'i' for insane mode, or 'g' for GOD mode";
		} else {
			instrucText.text = "";
			instrucText2.text = "";
		}
	}

	void SetCountText () {
		countText.text = "Blocks collected: " + (count).ToString ();
		if (count == numBlocks) {
			self.gameObject.SetActive (false);
			winText.text = "You Win!!!";
			scoreText.text = "Score: " + count;
			resetText.text = "Press enter to reset";
			countText.text = "";
		}
	}
	void YouLose () {
		self.gameObject.SetActive (false);
		winText.text = "You lose!!!";
		scoreText.text = "Blocks collected: " + (count).ToString () + "\nScore multiplier: " + scoreFactor +
			"\nScore: " + (count * scoreFactor).ToString();
		resetText.text = "Press enter to reset";
		countText.text = "";
	}
}