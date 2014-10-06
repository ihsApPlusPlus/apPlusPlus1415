using UnityEngine;
using System.Collections;

public class Rotater : MonoBehaviour {
	float timer = 0.5f;

	void Update () {
		transform.Rotate (new Vector3 (30, 60, 90) * Time.deltaTime);
		UpdateColor ();
	}
	void UpdateColor() {
		timer += Time.deltaTime;
		if (timer >= 0.5f) {
			Color newColor = new Color (Random.value, Random.value, Random.value, 0.5f);
			renderer.material.color = newColor;
			timer = 0.0f;
		}
	}
}