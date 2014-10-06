using UnityEngine;
using System.Collections;

public class SidetoSide : MonoBehaviour {

	void Start () {

	}

	void Update () {
		transform.position = new Vector3(Mathf.PingPong(2 * Time.time, 25) - 78, transform.position.y, transform.position.z);
	}
}
