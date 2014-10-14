using UnityEngine;
using System.Collections;

public class UpDown : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		transform.position = new Vector3(transform.position.x - 20, Mathf.PingPong(2 * Time.time, 23), transform.position.z);
	}
}
