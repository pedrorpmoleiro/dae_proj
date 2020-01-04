<template>
  <div v-if="userLogado.tipo == 'ADMINISTRADOR'">
    <v-btn class="ma-2" color="blue darken-2" to="/utilizadores" dark>
      <v-icon dark left>mdi-arrow-left</v-icon>Back
    </v-btn>
    <h1>Create a new Socio</h1>
    <form ref="form" @submit.prevent="create" :disabled="!isFormValid" lazy-validation>
      <v-text-field v-model.trim="name" :counter="30" :rules="nameRules" label="Nome" required />
      <v-text-field
        v-model.trim="email"
        :counter="50"
        type="email"
        :rules="emailRules"
        label="E-mail"
        ref="email"
        required
      />
      <v-text-field
        v-model.trim="username"
        :counter="35"
        :rules="usernameRules"
        label="Username"
        required
      />
      <v-text-field
        v-model.trim="password"
        :rules="passwordRules"
        type="password"
        label="Password"
        required
      />
      <v-select
        v-model="tipo"
        :items="items"
        :rules="[v => !!v || 'Tipo is required']"
        label="Tipo"
        required
      />
      <v-btn :disabled="!isFormValid" color="success" class="mr-4" @click="create">Adicionar</v-btn>

      <v-btn color="error" class="mr-4" @click="reset">Limpar</v-btn>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userLogado:{
        tipo: undefined,
        username: undefined
      },
      username: null,
      password: null,
      name: null,
      email:null,
      isUserAutenticado: false,
      nameRules: [
        v => !!v || "Name is required",
        v =>
          (v && v.length <= 30 && v.length >= 3) ||
          "Name must be less than 30 characters and more than 3"
      ],
      emailRules: [
        v => !!v || "E-mail is required",
        v => /.+@.+\..+/.test(v) || "E-mail must be valid"
      ],
      tipo: null,
      items: ["ADMINISTRADOR", "TREINADOR", "ATLETA", "SOCIO SIMPLE"],
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
      ],
      errorMsg: false
    };
  },
  mounted(){
    if (this.userLogado.username != null) {
  this.isUserAutenticado = true;
} else {
  let userUsername = localStorage.getItem("username");

  if (userUsername == null) {
    console.log("Não esta logado");
    this.$router.push("/login");
    return;
  }
  console.log(userUsername);
  this.$axios({
    method: "get",
    url: "api/users/" + userUsername,
    headers: { "Content-Type": "application/json" }
  })
          .then(response => {
            //console.log(response)
            this.userLogado.tipo = response.data.tipo;
            this.userLogado.username = response.data.username;
          })
          .catch(error => {
              console.log(error);
              console.log(this.$axios.defaults.headers);
            });
}
  },
  computed: {
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
    isNameValid() {
      if (!this.name) {
        return null;
      }

      let nameLen = this.name.length;

      return !(nameLen < 3 || nameLen > 30);
    },
    isEmailValid() {
      if (!this.email) {
        return null;
      }

      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(this.email);
    },
    isTypeValid() {
      if (!this.tipo) {
        return false;
      }

      return this.items.includes(this.tipo);
    },
    isFormValid() {
      if (!this.isUsernameValid) {
        return false;
      }
      if (!this.isPasswordValid) {
        return false;
      }
      if (!this.isNameValid) {
        return false;
      }
      if (!this.isEmailValid) {
        return false;
      }
      if (!this.isTypeValid) {
        return false;
      }
      return true;
    }
  },
  methods: {
    reset() {
      this.$refs.form.reset();
    },
    create() {
      const crypto = require("crypto");
      const hash = crypto.createHash("sha256");
      hash.update(this.password);
      var passwordHash = hash.digest("base64");
      switch (this.tipo) {
        case "ADMINISTRADOR":
          this.$axios
            .$post("/api/users/administradores", {
              username: this.username,
              password: passwordHash,
              name: this.name,
              email: this.email
            })
            .then(() => {
              this.$router.push("/utilizadores");
            })
            .catch(error => {
              this.errorMsg = error.response.data;
            });

          break;
        case "TREINADOR":
          this.$axios
            .$post("/api/users/treinadores", {
              username: this.username,
              password: passwordHash,
              name: this.name,
              email: this.email
            })
            .then(() => {
              this.$router.push("/utilizadores");
            })
            .catch(error => {
              this.errorMsg = error.response.data;
            });
          break;
        case "ATLETA":
          this.$axios
            .$post("/api/users/atletas", {
              username: this.username,
              password: passwordHash,
              name: this.name,
              email: this.email
            })
            .then(() => {
              this.$router.push("/utilizadores");
            })
            .catch(error => {
              this.errorMsg = error.response.data;
            });
          break;
        case "SOCIO SIMPLE":
          this.$axios
            .$post("/api/users", {
              username: this.username,
              password: passwordHash,
              name: this.name,
              email: this.email
            })
            .then(() => {
              this.$router.push("/utilizadores");
            })
            .catch(error => {
              this.errorMsg = error.response.data;
            });
          break;
        default:
          this.errorMsg = "Error : Type undefined";
          break;
      }
    }
  }
};
</script>
