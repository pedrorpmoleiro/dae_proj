<template>
  <v-content>
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="4">
          <v-card class="elevation-12">
            <v-toolbar color="indigo lighten-1" dark flat>
              <v-toolbar-title>Login</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field
                  label="Username"
                  v-model="username"
                  name="UsernameLogin"
                  prepend-icon="mdi-account"
                  :rules="usernameRules"
                  type="text"
                />
                <v-text-field
                  id="password"
                  v-model="password"
                  label="Password"
                  name="passwordLogin"
                  prepend-icon="mdi-lock"
                  :rules="passwordRules"
                  type="password"
                />
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn color="indigo lighten-1" class="white--text" @click="logIn">Login</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
export default {
  data() {
    return {
      username: undefined,
      password: undefined,
      usernameRules: [
        v => !!v || "UserName is required",
        v =>
          (v && v.length <= 35 && v.length >= 4) ||
          "UserName must be less than 35 characters and more than 4"
      ],
      passwordRules: [
        v => !!v || "Password is required",
        v =>
          (v && v.length <= 20 && v.length >= 6) ||
          "Password must be less than 20 characters and more than 6 characters"
      ]
    };
  },
  methods: {
    isUsernameValid() {
      if (!this.username) {
        return null;
      }

      let usernameLen = this.username.length;

      return !(usernameLen < 4 || usernameLen > 35);
    },
    isPasswordValid() {
      if (!this.password) {
        return null;
      }

      let passwordLen = this.password.length;

      return !(passwordLen < 6 || passwordLen > 20);
    },
    isFormValid() {
      if (!this.isUsernameValid()) {
        return false;
      }

      return this.isPasswordValid();
    },
    logIn() {
      if (!this.isFormValid()) {
        console.log("ERROR: No form");
        return;
      }

      const crypto = require("crypto");
      const hash = crypto.createHash("sha256");
      hash.update(this.password);
      var passwordHash = hash.digest("base64");
      // console.log(passwordHash);

      var data = JSON.stringify({
        username: this.username,
        password: passwordHash
      });
      //console.log(data)
      this.$axios({
        method: "post",
        url: "api/users/login",
        data: data,
        headers: { "Content-Type": "application/json" }
      })
        .then(() => {
          console.log("LOGIN");
          localStorage.setItem("username", this.username);
          this.$router.push("/profile");
        })
        .catch(error => {
          console.log(error);
          console.log(this.$axios.defaults.headers);
        });
    }
  }
};
</script>
