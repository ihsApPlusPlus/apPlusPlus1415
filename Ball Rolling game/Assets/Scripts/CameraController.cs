using UnityEngine;
using System.Collections;

public class CameraController : MonoBehaviour {
	public GameObject player;
	private Vector3 offset;
	
	void Start () {
		offset = transform.position;
	}

	void Update  () {  
		if (Input.GetKeyDown (KeyCode.Return)) {  
			Application.LoadLevel (0);  
		}  
	}

	void LateUpdate () {
		transform.position = player.transform.position + offset;
	}
}
