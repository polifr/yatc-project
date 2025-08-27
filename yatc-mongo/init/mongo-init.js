db = db.getSiblingDB("yatc")

db.createUser({
    user: "yatc_app",
    pwd: "yatc_app",
    roles: [
        { role: "readWrite", db: "yatc" }
    ]
})

db.createUser({
    user: "yatc_usr",
    pwd: "yatc_usr",
    roles: [
        { role: "read", db: "yatc" }
    ]
})
