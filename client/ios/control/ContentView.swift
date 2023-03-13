//
//  ContentView.swift
//  control
//
//  Created by Vladislav Sumin on 13.03.2023.
//

import SwiftUI
import common

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text(HelloProvider.init().getHello())
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
